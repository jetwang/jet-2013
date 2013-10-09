(function (root) {
    require(["./config"], function () {
        require([
            "angularRoute",
            "angularBootstrap",
            "./common/route",
            "angular",
            "globalParams",
            "text!./common/template/default_layout.html",
            "./user/service/currentUserService",
            "./user/userControllers"], function (angularRoute, angularBootstrap, app, angular, globalParams, layoutHTML) {
            var $container = angular.element(document.getElementById('ui-app-container'));
            angular.element().ready(function () {
                $container.html(layoutHTML);
                globalParams.apiHost=$container.attr("api-host");
                $container.addClass('ng-app');
                angular.bootstrap($container, ["uiapp"]);
            });
        });
    });
})(this);

