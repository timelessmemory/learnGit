String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g,"");
}

String.prototype.ltrim = function() {
    return this.replace(/(^\s*)/g,"");
}

String.prototype.rtrim = function() {
    return this.replace(/(\s*$)/g,"");
}

/*function $(selector) {
    return document.getElementById(selector);
}*/

function isInt(str) {
    var reg = /^[0-9]*[1-9][0-9]*$/;
    return reg.test(str);
}

function isDot(str) {
    return /^\d+\.\d+$/.test(str);
}

function isIntOrDot(str) {
    var reg = /^[0-9]*[1-9][0-9]*$/;
    return reg.test(str) || /^\d+\.\d+$/.test(str);
}

function hideTipDialog() {
    document.getElementById("mask").style.display = "none";
    document.getElementById("tipDialog").style.display = "none";
}

function htmlEncode(text) {
    if (text != null) {
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("&", "&amp;");

        text = text.replace("&amp;amp;", "&amp;");
        text = text.replace("&amp;quot;", "&quot;");
        text = text.replace("&amp;lt;", "&lt;");
        text = text.replace("&amp;gt;", "&gt;");
        text = text.replace("&amp;nbsp;", "&nbsp;");
    }
    return text;
}