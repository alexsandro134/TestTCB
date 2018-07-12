function addMonth(inputDate, x) {
    if (isDate_2(inputDate)) {
        var day, month, year;
        month = inputDate.substring(4, 6) - 1;
        day = inputDate.substring(6, 8) - 0;
        year = inputDate.substring(0, 4) - 0;
        var currentDate = new Date(year, month, day);
        currentDate.setMonth(currentDate.getMonth() + parseInt(x));
        document.getElementById('resultAddMonth').innerHTML = currentDate.toLocaleDateString('en-US');
    } else
        document.getElementById('resultAddMonth').innerHTML = "Invalid Date, please input correct";
}

function monthDiff(startInput, closeInput) {
    if (isDate_2(startInput) && isDate_2(closeInput)) {
        var startDate, startDay, startMonth, startYear, closeDate, closeMonth, closeDay, closeYear;
        startMonth = startInput.substring(4, 6) - 1;
        startDay = startInput.substring(6, 8) - 0;
        startYear = startInput.substring(0, 4) - 0;
        startDate = new Date(startYear, startMonth, startDay);
        closeMonth = closeInput.substring(4, 6) - 1;
        closeDay = closeInput.substring(6, 8) - 0;
        closeYear = closeInput.substring(0, 4) - 0;
        closeDate = new Date(closeYear, closeMonth, closeDay);
        var monthDifferent = closeDate.getMonth() - startDate.getMonth() + (12 * (closeDate.getFullYear() - startDate.getFullYear()));
        document.getElementById('result').innerHTML = monthDifferent + " month(s) between";
    } else
        document.getElementById('result').innerHTML = "Invalid Date, please input correct";
}

function dayDiff(startInput, closeInput) {
    if (isDate(startInput) && isDate(closeInput)) {
        var msInADay = 24 * 60 * 60 * 1000;
        var startDate, startDay, startMonth, startYear, closeDate, closeMonth, closeDay, closeYear;
        startMonth = startInput.substring(4, 6) - 1;
        startDay = startInput.substring(6, 8) - 0;
        startYear = startInput.substring(0, 4) - 0;
        startDate = new Date(startYear, startMonth, startDay);
        closeMonth = closeInput.substring(4, 6) - 1;
        closeDay = closeInput.substring(6, 8) - 0;
        closeYear = closeInput.substring(0, 4) - 0;
        closeDate = new Date(closeYear, closeMonth, closeDay);
        daysBetween = (closeDate.getTime() - startDate.getTime()) / msInADay;
        document.getElementById('result').innerHTML = daysBetween + " day(s) between";
    } else
        document.getElementById('result').innerHTML = "Invalid Date, please input correct";
}

function isDate(inputDate) {
    var objDate,
        mSeconds,
        day,
        month,
        year;
    if (inputDate.length !== 8) {
        return false;
    }
    month = inputDate.substring(4, 6) - 1;
    day = inputDate.substring(6, 8) - 0;
    year = inputDate.substring(0, 4) - 0;
    if (year < 1000 || year > 3000) {
        return false;
    }
    mSeconds = (new Date(year, month, day)).getTime();
    objDate = new Date();
    objDate.setTime(mSeconds);
    if (objDate.getFullYear() !== year ||
        objDate.getMonth() !== month ||
        objDate.getDate() !== day) {
        return false;
    }
    return true;
}