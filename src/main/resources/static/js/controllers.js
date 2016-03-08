var laganizerControllers = angular.module('laganizerControllers', ['restangular']);


laganizerControllers.controller('UserCtrl', ['$scope' , 'Restangular', function($scope, Restangular) {

                        //Example for setting values in AngularJS via Thymeleaf. (Only good if initial value is only
                        //required at initial loading of page. See index.html for example how to call these methods.
                         $scope.setUser = function(user) {
                             $scope.user = user;
                         }
                         $scope.setFirstName = function(firstName) {
                              $scope.firstName = firstName;
                         }

                        //Creates a Restangular Service, which uses http://localhost:8080/users as base url.
                        var restService = Restangular.all('users');
                        //Sends a GET Request to baseURL + 'list', and calls the specified function once the call has returned.
                        restService.get('list').then(function (users) {
                            $scope.users = users;
                        });

                        restService.get('current').then(function (user) {
                            $scope.currentUser = user;
                        });

                        //Specified the updateName Method.
                        $scope.updateUser = function() {
                            //Wir senden den currentUser, den wir oben geladen haben, modifiziert zurück.
                            var data = $scope.currentUser;
                            //Erstellt einen Post Request an base url + update + data.id mit data als Parameter.
                            restService.one('update').post(data.id, data).then(function() {
                                console.log("User saved OK");
                            }, function() {
                                console.log("Error saving User");
                            });
                        };
                    }]);



                    /* This is the command if you do not use restangular and jackson-JSON Conversion:

                                             $scope.updateName = function() {
                                                var name = $scope.users[0].name;
                                                var id = $scope.users[0].id
                                                var answerParam = {
                                                    "id":id,
                                                    "name":name
                                                }
                                                $http({
                                                    method: 'POST',
                                                    url: 'users/1',
                                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                                                    transformRequest: function(obj) {
                                                        var str = [];
                                                        for(var p in obj)
                                                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                                                        return str.join("&");
                                                    },
                                                    data: {id: $scope.users[0].id, name:$scope.users[0].name}
                                                });
                                             };*/