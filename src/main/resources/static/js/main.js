var getParameterByName = function (name, fallback) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");

    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);

    if (fallback === undefined) {
        fallback = null;
    }

    return results ? decodeURIComponent(results[1].replace(/\+/g, " ")) : fallback;
};

var app = angular.module("main-app", []);

app.service('apiServices', function () {
    /*this.getSubscriptionDetails = function (callback) {
        var request = {}, errCb, successCb;

        if ($.cookie("h")) {
            request.url = "/hash/" + $.cookie("h");
        } else {
            request.url = "/msisdn/" + $.cookie("m");
        }
        errCb = function (error) {
            callback(error);
        };

        successCb = function (data) {
            callback(null, data);
        };

        request.type = "get";

        request.success = successCb;
        request.error = errCb;
        $.ajax(request);
    };*/

    this.getGamesByCategory = function (callback) {
        var request = {}, errCb, successCb;
        errCb = function (error) {
            callback(error);
        };

        successCb = function (data) {
            callback(null, data);
        };

        request.type = "get";
        request.url = "/api/video/home";
        request.success = successCb;
        request.error = errCb;
        $.ajax(request);
    };
});

app.controller("mainCtrl", ['$scope', 'apiServices', function ($scope, apiServices) {

}]);

app.controller("homeCtrl", ['$scope', 'apiServices', '$timeout',function ($scope, apiServices, $timeout) {
    $scope.bannerImages = ["cooking Tips.jpg", "cooking-tips-copied-2.jpg", "Health tips.jpg", "makeup tips.jpg"]

    apiServices.getGamesByCategory(function (error, data) {
        $scope.videosByCategory = data;
        $scope.$apply();
    });
    $scope.playVideo = function (val) {
         $timeout(function () {
             $scope.currentSelected = val;
             if ($scope.recommendedVideos === undefined) {
                $scope.recommendedVideos = $scope.videosByCategory[val.category];
             }

             $scope.$apply();
             $(".video video source").attr("src", "https://femvideos.s3.us-east-2.amazonaws.com/" + val.videoUrl);
             $(".video video").attr("src", "https://femvideos.s3.us-east-2.amazonaws.com/" + val.videoUrl);
             $('#playModel').modal('show');
         }, 100);

    }
    $('#playModel').on('shown.bs.modal', function (e) {
      $('.video').bind('contextmenu', function(e) {
          return false;
      });
    });

    $('#playModel').on('hidden.bs.modal', function () {
        $(".video video").each(function () { this.pause() });
    });
}]).directive("owlCarousel", function() {
   	return {
   		restrict: 'E',
   		transclude: false,
   		link: function (scope) {
   			scope.initCarousel = function(element) {
   			  // provide any default options you want
   				var defaultOptions = {
   				margin: 5,
   				animateOut: 'fadeOut',
   				animateIn: 'fadeIn',
   				lazyLoad: true,
   				dots: false,
                 smartSpeed: 800,
                 nav: true,
                 navigation: false,
                pagination: true,
                 rewindNav : false,
                 responsive: {
                     0: {
                       items: 3
                     },

                     600: {
                       items: 3
                     },

                     1024: {
                       items: 4
                     },

                     1366: {
                       items: 4
                     }
                   }
   				};
   				var customOptions = scope.$eval($(element).attr('data-options'));
   				// combine the two options objects
   				for(var key in customOptions) {
   					defaultOptions[key] = customOptions[key];
   				}
   				// init carousel
   				var curOwl = $(element).data('owlCarousel');
   				if(!angular.isDefined(curOwl)) {
     				$(element).owlCarousel(defaultOptions);
   				}
   				scope.cnt++;
   			};
   		}
   	};
   })
   .directive('owlCarouselItem', [function() {
   	return {
   		restrict: 'A',
   		transclude: false,
   		link: function(scope, element) {
   		  // wait for the last item in the ng-repeat then call init
   			if(scope.$last) {
   				scope.initCarousel(element.parent());
   			}
   		}
   	};
   }]);

app.controller("playCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {

}]);

app.controller("subscribeCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {
    $scope.confirmSubscription = function () {

    };
}]);

app.controller("responseCtrl", ['$scope', 'apiServices', '$timeout',function ($scope, apiServices, $timeout) {
    console.log(window.msisdn);
    console.log(window.error);
}]);
