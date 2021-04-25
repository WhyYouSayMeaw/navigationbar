package com.seniorproject.test

class Reviews {
    var cname : String = ""
    var user_name : String = ""
    var ratings : String = ""
    var reviews : String = ""
    var timestamp : String = ""
    var profile : String = ""
    constructor(cname:String ,user_name:String, profile:String, ratings:String,reviews:String,timestamp:String){
        this.cname = cname
        this.user_name = user_name
        this.profile = profile
        this.ratings = ratings
        this.reviews = reviews
        this.timestamp = timestamp
    }
}