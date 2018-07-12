var moment = require('moment');

function isDate_2(inputDate) {
    if (!moment(inputDate, 'YYYYMMDD', true).isValid()) {
        return false;
    } else {
        return true;
    }
}

function addMonth_2(inputDate, noOfMonths) {
    if (isDate_2(inputDate)) {
        var afterAddMonth = moment(inputDate).add(noOfMonths, 'M');
        document.getElementById('resultAddMonth').innerHTML = afterAddMonth.format('YYYYMMDD');
    } else {
        document.getElementById('resultAddMonth').innerHTML = "Invalid Date, please input correct";
    }
}

function monthDiff_2(startTime, endTime) {
    if (isDate_2(startTime) && isDate_2(endTime)) {
        var start = moment(startTime);
        var end = moment(endTime);
        document.getElementById('result').innerHTML = start.diff(end, 'Month') + ' months';
    }
}

function dayDiff_2(startTime, endTime) {
    if (isDate_2(startTime) && isDate_2(endTime)) {
        var start = moment(startTime);
        var end = moment(endTime);
        document.getElementById('result').innerHTML = start.diff(end, 'days') + ' days';
    }
}