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
});

app.controller("mainCtrl", ['$scope', 'apiServices', function ($scope, apiServices) {

}]);

app.controller("homeCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {
    $scope.items2 = [
            {
               imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
               caption:"Caption"
            },
            {
               imgurl:"http://desktop-backgrounds-org.s3.amazonaws.com/400x300/twitter-nature-high-definition.jpg",
               caption:"Caption"
            },
             {
              imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                caption:"Caption"
               },
               {
                imgurl:"http://desktop-backgrounds-org.s3.amazonaws.com/400x300/twitter-nature-high-definition.jpg",
                    caption:"Caption"
                },
               {
                   imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                    caption:"Caption"
               },
               {
                      imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                       caption:"Caption"
                 },
                  {
                  imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                  caption:"Caption"
                   },
                   {
                      imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                            caption:"Caption"
                },
                        {
                        imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                        caption:"Caption"
                         },
                          {
                         imgurl:"https://i.pinimg.com/originals/84/67/26/846726299dc5abbeb5d60016f0fb32e9.jpg",
                             caption:"Caption"
                        },

        ];
          /*$scope.items2 = [1,2,3,4,5,6,7,8,9,10];*/
}]).directive("owlCarousel", function() {
   	return {
   		restrict: 'E',
   		transclude: false,
   		link: function (scope) {
   			scope.initCarousel = function(element) {
   			  // provide any default options you want
   				var defaultOptions = {
   				lazyLoad: true,loop: true,margin: 20,
   				animateOut: 'fadeOut',
   				animateIn: 'fadeIn',
   				 autoHeight: true,
                 autoplayTimeout: 7000,
                 smartSpeed: 800,
                 nav: true,
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
    $('#playModel').on('shown.bs.modal', function (e) {
      $('.video').bind('contextmenu', function(e) {
          return false;
      });
    });
}]);

