function addMonth(inputDate, x) {
    var currentDate = new Date(inputDate);
    currentDate = currentDate.setMonth(currentDate.getMonth() + x);
    document.getElementById('resultAddMonth').innerHTML = currentDate;
}
function monthDiff(startInput, closeInput) {
    startDate = new Date(startInput);
    closeDate = new Date(closeInput);
    var monthDifferent = closeDate.getMonth() - startDate.getMonth() + (12 * (closeDate.getFullYear() - startDate.getFullYear()));
    document.getElementById('result').innerHTML = monthDifferent + " months between";
}
function dayDiff(startInput, closeInput) {
    var msInADay = 24 * 60 * 60 * 1000,
    startDate = new Date(startInput),
    closeDate = new Date(closeInput),
    daysBetween = (closeDate.getTime() - startDate.getTime()) / msInADay;
    document.getElementById('result').innerHTML = daysBetween + " days between";
}