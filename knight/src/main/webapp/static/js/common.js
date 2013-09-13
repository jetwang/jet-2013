var globalXMLRequest;
var $byId = function (id, frame) {
    if (frame)
        return frame.document.getElementById(id);
    else
        return document.getElementById(id);
};
var $byName = function (name) {
    return document.getElementsByName(name)[0];
};
var $win = function (name) {
    var win = eval(name);
    if (win.name)
        return win;
    for (var i = 0; i < window.frames.length; i++) {
        if (window.frames[i].name == name)
            return window.frames[i];
    }
    return null;
};

if (typeof y == 'undefined' || !y) {
    y = {};
}

y.limitContentLength = function (input, maxlength) {
    var value = $.trim(input.value);
    var length = value.length;
    if (length > parseInt(maxlength))
        $(input).val(value.substring(0, parseInt(maxlength)));
};

y.onlyKeepDigits = function (obj) {
    if (obj.value.match(/\D/g)) {
        $(obj).val($(obj).val().replace(/\D/g, ''));
    }
};

y.onlyKeepEnglish = function (obj) {
    if (obj.value.match(/[^a-z A-Z]/g)) {
        $(obj).val($(obj).val().replace(/[^a-z A-Z]/g, ''));
    }
};

y.onlyKeepFloat = function (obj) {
    var value = $(obj).val();
    if (isNaN(value)) {
        var arr = value.split(".");
        if (arr.length > 0) {
            value = arr[0].replace(/\D/g, '');
            if (value.length < 1) {
                value = 0;
            }
            var end = '';
            for (var i = 1; i < arr.length; i++) {
                end = end + '' + arr[i].replace(/\D/g, '');
            }
            if (end.length > 0) {
                value = value + '.' + end;
            }
        }
    }
    $(obj).val(value);
    return value;
};

y.replacePageNum = function (input, pageNum) {
    $(input).val($(input).val().replace(/\D/g, ''));
    var value = $(input).val();
    if (parseInt(value) < 1)
        $(input).val(1);
    if (parseInt(value) > pageNum)
        $(input).val(pageNum);
};

y.isIE6 = function () {
    return ($.browser.msie && ($.browser.version == "6.0") && !$.support.style);
};

y._qMessageAreaDiv = null;
function _qMessageAreaDiv() {
    if (y._qMessageAreaDiv == null) {
        y._qMessageAreaDiv = $("#messageContainer");
    }
    return y._qMessageAreaDiv;
}

y.addMessage = function (message, field) {
    if (field) {
        var errorSpan = $("#error_span_for_" + field);
        if (errorSpan.size() > 0) {
            errorSpan.append(message);
        } else {
            _qMessageAreaDiv().append("<p>" + message + "</p>");
        }
        return;
    }
    _qMessageAreaDiv().append("<p>" + message + "</p>");
};

y.resetHighLight = function () {
    $("div.error").removeClass("error");
};

y.highLight = function (fields, sourceIds) {
    if (fields) {
        var field_array = fields.split(",");
        for (var i = 0; i < field_array.length; i++) {
            var key = field_array[i];
            var input = $byName(key);
            if (!input) {
                input = $byId(key);
            }
            $(input).parents("div.control-group:first").addClass("error");
        }
    }
};

y.resetMessage = function () {
    $("span[id*=error_span_for]").html("");
    _qMessageAreaDiv().html("");
    _qMessageAreaDiv().attr("class", "");
    _qMessageAreaDiv().show();
};

y.error = function (message) {
    y.resetMessage();
    _qMessageAreaDiv().addClass("alert alert-error");
    y.addMessage(message);
};

y.prompt = function (message) {
    y.resetMessage();
    _qMessageAreaDiv().addClass("alert alert-success");
    _qMessageAreaDiv().html(message);
};

y.openWindow = function (url, width, height) {
    if (!width)
        width = 350;
    if (!height)
        height = 350;
    return window.open(url, "_new", "menubar=0,scrollbars=1,resizable=1,width=" + width + ",height=" + height);
};

y.openOnlyWindow = function (url, width, height) {
    if (y.onlyWindow) {
        if (y.onlyWindow.closed) {
            y.onlyWindow = y.openWindow(url, width, height);
        } else {
            y.onlyWindow.close();
        }
        y.onlyWindow = y.openWindow(url, width, height);
    } else {
        y.onlyWindow = y.openWindow(url, width, height);
    }
    y.onlyWindow.focus();
};

y.addDatePicker = function (element, minDate, maxDate) {
    $(element).datepicker({
        dateFormat:"mm/dd/yy",
        minDate:minDate,
        maxDate:maxDate,
        yearRange:'1900:2100'
    });
};

y.removeDatePicker = function (element) {
    $(element).datepicker("destroy").removeAttr("readonly");
};

y.decorateDatePickers = function (minDate, maxDate) {
    $("input.datepicker").each(function () {
        y.addDatePicker(this, minDate, maxDate);
    });
};

y.request = function (url) {
    try {
        if (globalXMLRequest == undefined) {
            if (window.XMLHttpRequest) {
                globalXMLRequest = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                try {
                    globalXMLRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        globalXMLRequest = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e1) {
                        alert("no supported xml request");
                        /* XMLHTTPRequest not supported */
                    }
                }
            }
        }
        globalXMLRequest.open("GET", url, false);
        globalXMLRequest.send(null);
        return window["eval"]("(" + globalXMLRequest.responseText + ")");
    } catch (e) {
        y.setErrorMessage(yl.submitErrorMessage);
    }
};

y.utility = {
    countChecked:function (element) {
        var elements = $(element);
        while (!elements.get(0).nodeName.toLowerCase().match(/form|body/)) {
            elements.parent();
        }
        return elements.find('[name=' + element.name + ']').filter(':checked').length;
    },

    getLength:function (value, element) {
        var length;
        switch (element.nodeName.toLowerCase()) {
            case 'select':
                length = this.getSelectedOptions(element).length;
                break;
            case 'input':
                switch (element.type.toLowerCase()) {
                    case 'checkbox':
                        length = this.countChecked(element);
                        break;
                    default:
                        length = value.length;
                }
                break;
            default:
                length = value.length;
        }
        return length;
    },

    /**
     * Returns an array of all selected options of a
     * select element. Very useful to validate a select
     * with multiple="multiple".
     */
    getSelectedOptions:function (select) {
        return $("option:selected", select).get();
    },
    getSelectedValue:function (select) {
        return $("option:selected", select).val();
    },
    isRadioButtonSelected:function (radio) {
        var elements = document.getElementsByName(radio.name);
        for (var i = 0, element; element = elements[i]; i++) {
            if (element.checked) {
                return true;
            }
        }
        return false;
    },
    getRadioButtonValue:function (radioName) {
        var elements = document.getElementsByName(radioName);
        for (var i = 0, element; element = elements[i]; i++) {
            if (element.checked) {
                return element.value;
            }
        }
        return null;
    },
    getCheckBoxListValue:function (checkName) {
        var elements = document.getElementsByName(checkName);
        var values = [];
        for (var i = 0, element; element = elements[i]; i++) {
            if (element.checked) {
                values.push(element.value);
            }
        }
        return values.join(",");
    },
    selectContainsOption:function (select, value) {
        var options = select.options;
        for (var i = 0; i < options.length; i++) {
            if (options[i].value == value) {
                return true;
            }
        }
        return false;
    },
    selectAllOption:function (obj) {
        if (obj != null) {
            for (var i = 0; i < obj.length; i++) {
                obj.options[i].selected = true;
            }
        }
    },
    checkedAllByName:function (objName) {
        var checkFlag = false;
        var items = document.getElementsByName(objName);
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                if (!items[i].checked) {
                    checkFlag = true;
                }
            }
            for (var i = 0; i < items.length; i++) {
                items[i].checked = checkFlag;
            }
        }
    },
    checkedAllById:function (objId) {
        var bCheck = false;//When haven't checked exist;
        var oDiv = document.getElementById(id);
        var aInput = oDiv.getElementsByTagName("input");
        var aCheckbox = new Array();
        for (var i = 0; i < aInput.length; i += 1) {
            if (aInput[i].type == "checkbox") {
                aCheckbox.push(aInput[i]);
            }
        }
        for (var i = 0; i < aCheckbox.length; i += 1) {
            if (!aCheckbox[i].checked) {
                bCheck = true;
            }
        }
        if (bCheck) {
            for (var j = 0; j < aCheckbox.length; j += 1) {
                if (aCheckbox[j].type.toLowerCase() == "checkbox") {
                    aCheckbox[j].checked = true;
                }
            }
        } else {
            for (var k = 0; k < aCheckbox.length; k += 1) {
                if (aCheckbox[k].type.toLowerCase() == "checkbox") {
                    aCheckbox[k].checked = false;
                }
            }
        }
        if (arguments[1]) {
            eval(arguments[1]);
        }
    }
};

y.insertAtCursor = function (myField, myValue) {
    //IE support
    if (document.selection) {
        myField.focus();
        var sel = document.selection.createRange();
        sel.text = myValue;
    }
    //MOZILLA/NETSCAPE support
    else if (myField.selectionStart || myField.selectionStart == '0') {
        var startPos = myField.selectionStart;
        var endPos = myField.selectionEnd;
        myField.value = myField.value.substring(0, startPos)
                + myValue
                + myField.value.substring(endPos, myField.value.length);
    } else {
        myField.value += myValue;
    }
};

y.showWaiting = function () {
    var overlap = $("#overwrap");
    if (overlap.size() <= 0) {
        $(document.body).append("<div id='overwrap'><div id='loading'><img src='../images/CMP2/loading.gif' /></div><div id='overwrap-bg'></div>");
        overlap = $("#overwrap");
    }
    overlap.show();
};

y.hideWaiting = function () {
    top.$("#overwrap").hide();
};

y.showTempWaiting = function () {
    y.showWaiting();
    setTimeout(y.hideWaiting, 5000);
};

y.getIFrameDocument = function getIFrameDocument(iframeObj) {
    var rv = null;
    // if contentDocument exists, W3C compliant (Mozilla)
    if (iframeObj.contentDocument) {
        rv = iframeObj.contentDocument;
    } else {
        // IE
        rv = iframeObj.document;
    }
    return rv;
};

y.submitForm = function (form, url, target) {
    y.showWaiting();
    var oldUrl;
    var oldTarget;
    if (url) {
        form.action = url;
        oldUrl = form.action;
    }
    if (target) {
        form.target = target;
        oldTarget = form.target;
    } else {
        form.target = "_self";
    }
    form.submit();
    if (oldUrl) {
        form.action = oldUrl;
    }
    if (oldTarget) {
        form.target = oldTarget;
    }
    return false;
};

y.encode = function (string) {
    string = string.replace(/\"/g, "&quot;");
    string = string.replace(/\>/g, "&gt;");
    string = string.replace(/\</g, "&lt;");
    string = string.replace(/\n/g, "<br>");
    string = string.replace(/\s/g, "&nbsp;");
    return string;
};

y.recode = function (string) {
    string = string.replace(/&quot;/g, "\"");
    string = string.replace(/&amp;/g, "&");
    string = string.replace(/&gt;/g, ">");
    string = string.replace(/&lt;/g, "<");
    string = string.replace(/<br>/g, "\n");
    string = string.replace(/&nbsp;/g, "\s");
    return string;
};

y.openExportWindow = function (actionUrl) {
    if ($("#localAddress")[0]) {
        actionUrl = $("#localAddress").val() + actionUrl.replace("../", "");
        y.openOnlyWindow($("#localAddress").val() + "comm/exportView.htm?" + actionUrl, "", "80");
    } else {
        y.openOnlyWindow("../comm/exportView.htm?" + actionUrl, "", "80");
    }
};

y.confirm = function (question, submitEvent, cancelEvent) {
    var buttons = {};
    buttons[yl.cancel] = function () {
        if (cancelEvent) {
            cancelEvent();
        }
        y.closeDialog();
    };
    buttons[yl.confirm] = function () {
        submitEvent();
        y.closeDialog();
    };
    y.showDialog(buttons, yl.confirmTitle, y._getMessageDiv(question), 600, 160);
};

//disable ajax cache
$.ajaxSetup({
    cache:false
});

$(document).ajaxStart(function () {
//    y.showWaiting();
});

$(document).ajaxError(function () {
    y.setErrorMessage(y.submitErrorMessage);
});

$(document).ajaxComplete(function () {
    y.hideWaiting()
});

$(document).ready(function () {
    y.hideWaiting();
});

y.loadUrl = function (url) {
    url = url.replace(/\&amp;/g, '&');
    if (document.getElementById("ajaxDiv")) {
        $("#ajaxDiv").load(url);
    } else {
        location.href = url;
    }
};

y.pageSubmit = function (event, url, pageName) {
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    if (keyCode == 13) {
        y.goToPage(url, pageName);
        event.cancelBubble = true;
        event.returnValue = false;
    }
};

y.goToPage = function (url, pageName) {
    var u = url.replace(/\&amp;/g, '&').split("?");
    var href = u[0] + "?";
    if (u[1]) {
        var pageNum = document.getElementById(pageName + ".pageNumber").value;
        var s = u[1].split("&");
        var p = eval('/^' + pageName + '.pageNumber=.*$/');
        for (var i = 0; i < s.length; i++) {
            if (!p.exec(s[i])) {
                href += s[i] + "&";
            } else {
                href += pageName + ".pageNumber=" + pageNum + "&";
            }
        }
    }
    if (document.getElementById("ajaxDiv")) {
        $("#ajaxDiv").load(href);
    } else {
        location.href = href;
    }
};

y.supplant = function (template, data) {
    return template.replace(/{([^{}]*)}/g, function (match, pn) {
        return data[pn];
    });
};

y.escapeJQuerySpecialCharacter = function (str) {
    return str.replace(/([%#;&,\.\+\*~':"!\^\$\[\]\(\)=>|\/\\])/g, "\\$1");
};