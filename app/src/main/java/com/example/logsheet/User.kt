package com.example.logsheet

class User(name: String, logNumber: Int, date: String, startTime: String, endTime: String, location: String, battery: String, totalFlightTime: String, comments: String, preflight:String, packaging: String, homeBase: String,id:Int?) {
    var id: Int?=id
    var name: String? = name
    var logNumber: Int? = logNumber
    var date: String? = date
    var startTime: String? = startTime
    var endTime: String? = endTime
    var location: String? = location
    var battery: String? = battery
    var totalFlightTime: String? = totalFlightTime
    var comments: String? = comments
    var preflight: String? = preflight
    var packaging: String? = packaging
    var homeBase: String? = homeBase

}