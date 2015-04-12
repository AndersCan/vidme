interface ISecurityCtrl {
    username:string;
    password:string;
    $http: ng.IHttpService
    login:() => Boolean;
}

module Controllers {
    export class SecurityCtrl implements ISecurityCtrl {
        username:string;
        $http:ng.IHttpService;
        password:string;

        login() {
            console.log("LOGIN RUN");
            // Simple GET request example :
            this.$http.post('/security/login', {name: this.username, pw: this.password}).
                success(function (data, status, headers, config) {
                    // this callback will be called asynchronously
                    // when the response is available
                    console.log(data);
                    console.log(status);
                    console.log(headers);
                    console.log(config);
                }).
                error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    console.log(data);
                    console.log("Error")
                });
            return false;
        }

        message = "Welcome to Login!";

        constructor($scope, $http) {
            this.$http = $http;
            $scope.vm = this;
        }
    }
}
