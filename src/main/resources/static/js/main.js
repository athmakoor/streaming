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
    $scope.items2 = {"CATEGORY 1":[
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

        ],
        "CATEGORY 2":[
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

                ],
                "CATEGORY 3":[
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

                        ]};
    $scope.recommendedVideos = [
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
    apiServices.getGamesByCategory(function (error, data) {
        $scope.videosByCategory = data;
        $scope.$apply();
    });
    $scope.playVideo = function (val) {
         $timeout(function () {
             $scope.currentSelected = val;
             $("video source").attr("src", "/videos/sample.mp4");
             $("video").attr("src", "/videos/sample.mp4");
             $('#playModel').modal('show');
             $scope.$apply();
         }, 100);

    }
    $('#playModel').on('shown.bs.modal', function (e) {
      $('.video').bind('contextmenu', function(e) {
          return false;
      });
    });

    $('#playModel').on('hidden.bs.modal', function () {
        $("video").each(function () { this.pause() });
    });
}]).directive("owlCarousel", function() {
   	return {
   		restrict: 'E',
   		transclude: false,
   		link: function (scope) {
   			scope.initCarousel = function(element) {
   			  // provide any default options you want
   				var defaultOptions = {
   				margin: 0,
   				animateOut: 'fadeOut',
   				animateIn: 'fadeIn',
   				lazyLoad: true,
                 smartSpeed: 800,
                 nav: true,
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
