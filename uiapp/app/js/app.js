(function (root) {
    var uiappContainer = document.getElementById('ui-app-container');
    require(["./config"], function (config) {
        config(uiappContainer.getAttribute("js-host"));
        require([
            "angularRoute",
            "angularBootstrap",
            "angular",
            "globalParams",
            "text!./common/template/default_layout.html",
            "modules",
            "./common/interceptors/authenticationInterceptor",
            "./knight/services/knightService",
            "./knight/knightControllers",
            "./user/userControllers"], function (angularRoute, angularBootstrap, angular, globalParams, layoutHTML) {
            var $container = angular.element(uiappContainer);
            angular.element().ready(function () {
                $container.html(layoutHTML);
                globalParams.apiHost = $container.attr("api-host");
                $container.addClass('ng-app');
                console.log("bootstap uiapp start");
                angular.bootstrap($container, ["uiapp"]);
                console.log("bootstap uiapp done");
            });
        });
    });
})(this);

