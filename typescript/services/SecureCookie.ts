interface ISecureCookies {
    $cookieStore:angular.cookies.ICookieStoreService;
    login:(name:string) => void;
    logout:() => void;
    isLoggedIn:() => Boolean;
    getName:() => String;
}
class SecureCookie implements ISecureCookies {
    $cookieStore:angular.cookies.ICookieStoreService;

    static printHello() {
        console.log("Hello from SecureCookie!");
    }

    login(username:string) {
        this.$cookieStore.put("authenticated", true);
        this.$cookieStore.put("username", username);
    }

    logout() {
        this.$cookieStore.remove("authenticated");
        this.$cookieStore.remove("username");
    }

    isLoggedIn() {
        return this.$cookieStore.get("authenticated");
    }

    constructor($cookieStore) {
        this.$cookieStore = $cookieStore;
    }

    getName():string {
        return this.$cookieStore.get("username");
    }
}

services.service('SecureCookie', SecureCookie);

/*
 getSession() {
 // Something...
 // read Play session cookie
 console.log(this.$cookies);
 console.log(this.$cookies.put("TEST","TVALUE"));
 //var rawCookie = this.$cookies['PLAY_SESSION'];
 var rawCookie = this.$cookies.get("PLAY_SESSION");
 //var rawCookie = this.$cookies.get('TEST');
 console.log(rawCookie);
 var rawData = rawCookie.substring(rawCookie.indexOf('-') + 1, rawCookie.length - 1);
 var session = {};
 angular.forEach(rawData.split("&"), function (rawPair) {
 var pair = rawPair.split('=');
 session[pair[0]] = pair[1];
 });
 return session;
 }
 */