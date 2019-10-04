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

}]);

app.controller("playCtrl", ['$scope', 'apiServices',function ($scope, apiServices) {
    $('#playModel').on('shown.bs.modal', function (e) {
      $('.video').bind('contextmenu', function(e) {
          return false;
      });
    });
}]);

