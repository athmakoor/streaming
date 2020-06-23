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

    this.getGamesForHomePage = function (callback) {
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

    this.getGamesByCategory = function (category, callback) {
        var request = {}, errCb, successCb;
        errCb = function (error) {
            callback(error);
        };

        successCb = function (data) {
            callback(null, data);
        };

        request.type = "get";
        request.url = "/api/video/category/" + category;
        request.success = successCb;
        request.error = errCb;
        $.ajax(request);
    };

    this.getGamesDetailsById = function (id, callback) {
        var request = {}, errCb, successCb;
        errCb = function (error) {
            callback(error);
        };

        successCb = function (data) {
            callback(null, data);
        };

        request.type = "get";
        request.url = "/api/video/id/" + id;
        request.success = successCb;
        request.error = errCb;
        $.ajax(request);
    };
});

app.controller("mainCtrl", ['$scope', 'apiServices', function ($scope, apiServices) {

}]);

app.controller("homeCtrl", ['$scope', 'apiServices', '$timeout',function ($scope, apiServices, $timeout) {
    $scope.bannerImages = ["banner-2.png", "banner-1.jpg"];
    $scope.quickLinks = [
        {value: "ENGLISH LEAGUE", color: "rgb(0, 150, 136)"},
        {value: "SPANISH LEAGUE", color: "rgb(0, 188, 212)"},
        {value: "ITALIAN LEAGUE", color: "rgb(63, 81, 181)"},
        {value: "CHAMPIONS LEAGUE", color: "rgb(102, 0, 153)"}
    ]
    document.getElementById("back-btn").classList.add("no-visibility");
    //element.classList.remove("no-display");

    apiServices.getGamesForHomePage(function (error, data) {
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
             $(".video video source").attr("src", "https://mooddit.s3.ap-south-1.amazonaws.com/" + val.videoUrl);
             $(".video video").attr("src", "https://mooddit.s3.ap-south-1.amazonaws.com/" + val.videoUrl);
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
   				lazyLoad: false,
   				dots: true,
   				loop: true,
                 autoplay:true,
                 autoplayTimeout:3000,
                 nav: false,
                 navigation: false,
                pagination: false,
                 rewindNav : false,
                 responsive: {
                     0: {
                       items: 1
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
    var id = getParameterByName("view");
    var category = getParameterByName("cat");

    apiServices.getGamesDetailsById(id, function (error, data) {
        $scope.currentSelected = data;
        apiServices.getGamesByCategory(category, function (error, data) {
            $scope.videos = data;
            $scope.$apply();
        });
    });
}]);

app.controller("categoryCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {
    $scope.category = window.category;
    apiServices.getGamesByCategory(window.category, function (error, data) {
        $scope.videos = data;
        $scope.$apply();
    });
}]);

app.controller("subscribeCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {
    $scope.confirmSubscription = function () {

    };
}]);

app.controller("responseCtrl", ['$scope', 'apiServices', '$timeout',function ($scope, apiServices, $timeout) {
    console.log(window.msisdn);
    console.log(window.error);
}]);
