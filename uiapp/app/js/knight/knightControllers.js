define(['angular', 'globalParams', 'modules'], function (angular, globalParams, modules) {
    'use strict';
    return modules.controllers
        .controller('knight-list-ctrl', ['$http', '$scope', '$modal', 'knightStatuses', function ($http, $scope, $modal, knightStatuses) {
            $scope.search = function (pageNumber) {
                var pageSize = 3;
                $http.get(globalParams.apiHost + "/knight/quote",
                    {params: {pageSize: pageSize, pageNumber: pageNumber, keyword: $scope.keyword}})
                    .success(function (data) {
                        if (pageNumber > 1) {
                            for (var i = 0; i < data.length; i++) {
                                $scope.knights.push(data[i]);
                            }
                        } else {
                            $scope.knights = data;
                        }
                        $scope.hasMoreData = data.length >= pageSize;
                        $scope.pageNumber = pageNumber;
                    });
            };
            $scope.edit = function (knight) {
                var editingKnight = {};
                if (knight) {
                    angular.extend(editingKnight, knight);
                }
                var modalInstance = $modal.open({
                    templateUrl: 'js/knight/templates/edit.html',
                    controller: 'knight-edit-ctrl',
                    resolve: {
                        knight: function () {
                            return editingKnight;
                        }
                    }
                });
                modalInstance.result.then(function (editedKnight) {
                    if (editedKnight) {
                        if (angular.isUndefined(knight)) {
                            knight = {};
                            $scope.knights.push(knight);
                        }
                        angular.extend(knight, editedKnight);
                    }
                });
            };

            $scope.delete = function (knight) {
                $http.delete(globalParams.apiHost + "/knight/delete/" + knight.knightId)
                    .success(function (data) {
                        var index = $scope.knights.indexOf(knight);
                        $scope.knights.splice(index, 1);
                    });
            };

            $scope.hasMoreData = true;
            $scope.knightStatuses = knightStatuses;
            $scope.search(1);
        }])
        .controller('knight-edit-ctrl', ['$http', '$scope', 'knight', 'knightStatuses', '$modalInstance', function ($http, $scope, knight, knightStatuses, $modalInstance) {
            $scope.knight = knight;
            $scope.knightStatuses = knightStatuses;
            $scope.cancelEdit = function () {
                $modalInstance.close();
            };

            $scope.update = function () {
                $http.post(globalParams.apiHost + "/knight/update", knight)
                    .success(function (data) {
                        $modalInstance.close(data);
                    });
            };
        }])
        .controller('knight-all-ctrl', ['$http', '$scope', 'knightStatuses', function ($http, $scope, knightStatuses) {
            $scope.knightStatuses = knightStatuses;
            $http.get(globalParams.apiHost + "/knight/quote",
                {params: {pageSize: 999999, pageNumber: 1}})
                .success(function (data) {
                    $scope.knights = data;
                });
        }]);
});