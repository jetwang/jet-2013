(function (root) {
    var uiappContainer = document.getElementById('ui-app-container');
    require(["./config"], function (config) {
        config(uiappContainer.getAttribute("js-host"));
        require([
            "angularRoute",
            "angularBootstrap",
            "./common/modules",
            "angular",
            "globalParams",
            "text!./common/template/default_layout.html",
            "./user/service/currentUserService",
            "./knight/knightControllers",
            "./user/userControllers"], function (angularRoute, angularBootstrap, modules, angular, globalParams, layoutHTML) {
            var $container = angular.element(uiappContainer);
            angular.element().ready(function () {
                $container.html(layoutHTML);
                globalParams.apiHost = $container.attr("api-host");
                $container.addClass('ng-app');
                angular.bootstrap($container, ["uiapp"]);
            });
        });
    });
})(this);

