module Controllers {
    export class MainCtrl {
        secureCookie:SecureCookie;
        message = "Welcome Soldier!";

        getWelcome() {
            if (this.secureCookie.isLoggedIn()) {
                return "Hello there, " + this.secureCookie.getName()
            } else {
                return "Hello there, please login";
            }
        }

        constructor($scope, SecureCookie) {
            this.secureCookie = SecureCookie;
            $scope.vm = this;
        }
    }
}
