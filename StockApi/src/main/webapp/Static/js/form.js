$(document).ready(function () {
    $(".registration-button").on("click", function () {
        showDialog(getReginDialogParams());
    });
    $(".login-button").on("click", function () {
        showDialog(getLoginDialogParams());
    });
    $('.send-message').on("click", function () {
        showDialog(getContactUsDialogParams());
    });
    $('.edit-account').on("click", function () {
        showDialog(getAccountDialogParams());
    });
    $('.deal-feedback').on("click", function () {
        showDialog(getDealFeedbackDialogParams($('#dealId').val()));
    });
    if ($('#deal-form').length > 0) {
        var dealForm = $('#deal-form');
        initDealForm(dealForm, getContextRoot() + dealForm.attr("data-url"), dealForm.attr("data-id"));
    }
});

function sendFormData(formContainer, buttonDef, $dialogbox) {
    if (formContainer[0].checkValidity()) {
        var data = getFormData(formContainer);

        var param;
        if (buttonDef.login) {
            param = JSON.parse(data);
        } else {
            param = {json: data};
        }

        buttonDef.submitFunction(
            buttonDef,
            param,
            formContainer,
            $dialogbox);

        return false;
    } else {
        return true;
    }

    function getFormData(formContainer) {
        var data = {};
        var formElementNames = ["input", "select", "textarea"];
        for (var i = 0; i < formElementNames.length; i++) {
            var formElementName = formElementNames[i];
            var formElements = formContainer.find(formElementName);
            for (var j = 0; j < formElements.length; j++) {
                var name = formElements[j].getAttribute("name");
                var value = formElements[j].value;
                var type = formElements[j].getAttribute("type");
                addToResult(data, name, value, type, formElements[j]);
            }
        }
        data = JSON.stringify(data, "", 1);
        return data;

        function addToResult(toObject, name, value, type, formElement) {
            var dotIndex = name.indexOf(".");
            if (dotIndex < 0) {
                if (type == "file") {
                    if (formElement.fileData) {
                        toObject[name] = formElement.fileData;
                    }
                } else {
                    toObject[name] = value;
                }
            } else {
                var subObjectName = name.substr(0, dotIndex);
                var subObject = toObject[subObjectName];
                name = name.substr(dotIndex + 1);
                if (!subObject) {
                    subObject = {};
                    toObject[subObjectName] = subObject;
                }
                addToResult(subObject, name, value, type, formElement);
            }
        }
    }
}

function setFormInputs(formContainer, object) {
    var formElementNames = ["input", "select", "textarea", "img"];
    for (var i = 0; i < formElementNames.length; i++) {
        var formElementName = formElementNames[i];
        var formElements = formContainer.find(formElementName);
        for (var j = 0; j < formElements.length; j++) {
            var name = formElements[j].getAttribute("name");
            var value = getValue(object, name);
            if (formElements[j].getAttribute("type") != "file") {
                if (formElementName == "img") {
                    if (value) {
                        $(formElements[j]).attr("src", value);
                    }
                } else if ($(formElements[j]).attr("type") == "radio" && value) {
                    $(formElements[j]).prop("checked", true);
                } else {
                    formElements[j].value = value;
                }
            }
            if (formElementName == "select") {
                $(formElements[j]).change();
            }
        }
    }

    if (object.fieldsToRemove) {
        for (i = 0; i < object.fieldsToRemove.length; i++) {
            $("[name=" + object.fieldsToRemove[i] + "]").remove();
            $("[for=" + object.fieldsToRemove[i] + "]").remove();
        }
    }

    function getValue(object, name) {
        if (!name) {
            return null;
        }
        var dotIndex = name.indexOf(".");
        if (dotIndex < 0) {
            if (object[name]) {
                return object[name];
            } else {
                return "";
            }
        } else {
            var subObjectName = name.substr(0, dotIndex);
            var subObject = object[subObjectName];
            if (!subObject) {
                return "";
            }
            name = name.substr(dotIndex + 1);
            return getValue(subObject, name);
        }
    }
}

function processRequestResult(formContainer, answer, messageDiv, $dialogbox) {
    if (answer.result == 0) {
        if (answer.data) {
            document.location = getContextRoot() + answer.data;
        } else {
            location.reload()
        }
        return;
    } else if (answer.result == 2) {
        messageDiv.addClass("success-message");
        setFormInputs(formContainer, answer.data);
    } else if (answer.result == 1) {
        if ($dialogbox) {
            $dialogbox.remove();
        }
        return;
    } else {
        messageDiv.addClass("error-message");
    }
    if (answer.message) {
        messageDiv.text(l10n.translate(answer.message.split(",")));
    }
    if (messageDiv.length > 0) {
        messageDiv[0].scrollIntoView(true);
    }
    return answer.result;
}

function simpleButtonCallback(buttonDef, params, formContainer, $dialogbox) {
    $.post(buttonDef.submitUrl, params, function (answer) {
        if (typeof answer == 'string') {
            answer = JSON.parse(answer);
        }
        processRequestResult(formContainer, answer, $('.dialogbox-message'), $dialogbox);
    });
}

function buttonCallbackWithTableUpdate(buttonDef, params, formContainer, $dialogbox) {
    $.post(buttonDef.submitUrl, params, function (answer) {
        if (typeof answer == 'string') {
            answer = JSON.parse(answer);
        }
        var idColumnValue = JSON.parse(params.json)[buttonDef.model.idColumn];
        processRequestResult(formContainer, answer, $('.dialogbox-message'));
        if (answer.result > 0) {
            updateTable(idColumnValue, answer.data);
            $dialogbox.remove();
            if (buttonDef.submitUrl === "/lot/json/SaveAndActivate"
                || buttonDef.submitUrl === "/tender/json/SaveAndActivate") {
                if (new Date().getUTCHours() < 9 && new Date().getUTCHours() > 21) {
                    showMessageDialog("label.bid.activated-morning", "success");
                } else {
                    showMessageDialog("label.bid.activated-evening", "success");
                }
            } else if (buttonDef.submitUrl === "/lot/json/Save" || buttonDef.submitUrl === "/tender/json/Save") {
                showMessageDialog("label.bid.saved", "success");
            }
        }

        function updateTable(idColumnValue, object) {
            if (idColumnValue == "" || idColumnValue == -1) {
                buttonDef.table.DataTable().row.add(object).draw(false);
            } else {
                buttonDef.table.DataTable().rows().every(function () {
                    var d = this.data();
                    if (d[buttonDef.model.idColumn] == idColumnValue) {
                        this.data(object).draw(false);
                        return;
                    }
                });
            }
        }
    });
}

function initDealForm(formContainer, url, id) {
    $.post(url, {id: id}, function (answer) {
        if (typeof answer == 'string') {
            answer = JSON.parse(answer);
        }
        setFormInputs(formContainer, answer.data);
        if (answer.data.statusCode != 'ON_APPROVE') {
            $(".deal-button").attr("style", "display:none");
        } else {
            $(".deal-feedback").attr("style", "display:none");
        }
        $.getScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBap-4uJppMooA91S4pXWULgQDasYF1rY0&callback=initDealMap");
    }, "json");
}

function sendResetFormData(formContainer, url) {
    return sendFormData(
        $('#login-form'),
        {
            submitUrl: getContextRoot() + '/account/json/ResetPassword',
            submitFunction: simpleButtonCallback
        });
}

function sendDealFormData(url) {
    return sendFormData(
        $("#deal-form"),
        {
            submitUrl: getContextRoot() + url,
            submitFunction: callback
        });

    function callback(buttonDef, params, formContainer) {
        $.post(buttonDef.submitUrl, params, function (answer) {
            if (typeof answer == 'string') {
                answer = JSON.parse(answer);
            }
            processRequestResult(formContainer, answer, $('.form-message'));
            if (answer.result > 0) {
                $('.deal-button').attr("style", "display:none");
                $(".deal-feedback").attr("style", "");
            }
        });
    }
}


function openLoginForm() {
    $(".dialogbox").remove();
    showDialog(getLoginDialogParams());
}

function openRegistrationForm() {
    $(".dialogbox").remove();
    showDialog(getReginDialogParams());
}

function openPasswordResetForm() {
    $(".dialogbox").remove();
    showDialog(getPasswordResetDialogParams());
}

function loadCategories(productId) {
    $.post(getContextRoot() + "/category/json/FormList", {productId: productId}, function (answer) {
        if (typeof answer == 'string') {
            answer = JSON.parse(answer);
        }
        if (answer.result > 0) {
            var data = answer.data;
            var categorySelect = $("#category-select");
            $("#category-select option").remove();
            for (var i = 0; i < data.length; i++) {
                var option = new Option(data[i].categoryName, data[i].id);
                categorySelect.append($(option));
            }
            if ($("input[name=categoryId]").val()) {
                categorySelect.val($("input[name=categoryId]").val());
            }
        } else {
            console.log(answer);
        }
    });
}

function getContextRoot() {
    var contextRoot = $("meta[name=context-root]").attr("content");
    return contextRoot ? contextRoot : "";
}