interface ISecurityCtrl {
    username:string;
    password:string;
    $http: ng.IHttpService;
    $location: ng.ILocationService;
    secureCookie: SecureCookie;
    loginClick:() => void;
    getLoginMessage:()=>string;
}

interface IServerMessage {
    status: string;
    msg: string;
}

module Controllers {
    export class SecurityCtrl implements ISecurityCtrl {
        username:string;
        password:string;
        $http:ng.IHttpService;
        $location:ng.ILocationService;
        secureCookie:SecureCookie;

        postLogin() {
            // POST request with username and password
            return this.$http.post('/security/login', {name: this.username, pw: this.password});
        }

        loginClick() {
            if (this.secureCookie.isLoggedIn()) {
                // Already logged in!
                console.log("Already logged in!");
                this.$location.path("/")
            } else {

                var msg = this.postLogin();
                msg.success((data) => {
                    // this callback will be called asynchronously
                    // when the response is available
                    // Check if Login was Success
                    var response = <IServerMessage>data;

                    if (response.status == "notfound") {
                        // No user found
                        console.log("No user found");
                    } else if (response.status == "ok") {
                        // user found
                        console.log("Found user");
                        this.secureCookie.login(this.username);
                        this.$location.path("/")
                    } else {
                        // Unknown error...
                    }
                }).
                    error((data, status, headers, config) => {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                        console.log(data);
                    });
            }
        }

        getLoginMessage():string {
            if (this.secureCookie.isLoggedIn()) {
                return "You are already logged in as: " + this.secureCookie.getName();
            } else {
                return "Please login";
            }
        }


        message = "Welcome to Login!";

        constructor($scope, $http, $location, SecureCookie) {
            this.$http = $http;
            this.$location = $location;
            this.secureCookie = SecureCookie;
            $scope.vm = this;
        }
    }
}
