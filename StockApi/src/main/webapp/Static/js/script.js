$(document).ready(function () {

    if ((bowser.name != 'Chrome') && (bowser.name != 'Firefox') && (bowser.name != 'Safari')) {
        document.location = getContextRoot() + "/AnotherBrowser";
    }

    function selectLanguages() {
        $.widget("custom.iconselectmenu", $.ui.selectmenu, {
            _renderItem: function (ul, item) {
                var li = $("<li>"),
                    wrapper = $("<div>", {text: item.label});
                if (item.disabled) {
                    li.addClass("ui-state-disabled");
                }
                $("<span>", {
                    style: item.element.attr("data-style"), "class": "ui-icon " + item.element.attr("data-class")
                }).appendTo(wrapper);
                return li.append(wrapper).appendTo(ul);
            }
        });
        $("#languages-select").iconselectmenu().iconselectmenu("menuWidget").addClass("ui-menu-icons customicons");
        var selectedImage = $("#languages-select").find("option:selected").attr("data-class");
        $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage + '.png")');
        $(".preview-platform").css('background-image', 'url("/Static/img/' + selectedImage + '_responsive.png")');
        $("#languages-select").iconselectmenu({
            select: function (event, ui) {
                var selectedImage = $(this).find("option:selected").attr("data-class");
                $(".ui-selectmenu-text").css('background-image', 'url("/Static/img/' + selectedImage + '.png")');
                document.location = "?lang=" + $(".language-switcher").val().toLowerCase();
            }
        });
    }

    if ($("select").is("#languages-select")) {
        selectLanguages();
    }

    if ($('div').is('.gallery')) {
        baguetteBox.run('.gallery', {
            captions: false
        });
    }

    $(document).on("click", '#continue', function () {
        if ($('#registration-form')[0].checkValidity()) {
            $(".registration-first-part").hide();
            $(".registration-second-part").show();
            $(".registration-second-part input").attr("required", "required");
            return false;
        } else {
            return true;
        }
    });
    $(document).on("click", '.bgdark, .send-message', function () {
        $(".bgdark").hide();
        $(".aside").removeClass("aside-adaptive");
    });
    $(document).on("click", '.burger', function () {
        $(".aside").addClass("aside-adaptive");
        $(".bgdark").show();
    });
    $(document).on("click", '.burger-close', function () {
        $(".aside").removeClass("aside-adaptive");
        $(".bgdark").hide();
    });

    $(document).on("click", '.login-button-page', function () {
        if ($('.login-list').is(':visible')) {
            $('.login-list').fadeOut();
            $('.login-box').addClass("login-box-shadow");
        } else {
            $('.login-list').fadeIn();
            $('.login-box').removeClass("login-box-shadow");
        }
    });
    $(document).on('click', function (e) {
        if (!$(e.target).closest(".login-box").length) {
            $('.login-list').fadeOut();
        }
        e.stopPropagation();
    });

    $(document).on("click", '#go-back', function () {
        $(".registration-first-part").show();
        $(".registration-second-part").hide();
        $(".registration-second-part input").removeAttr("required");
    });

    if ($("a").is("#session-start")) {
        var count = 0;
        var counter = false;
        var isRunning = true;

        function interval() {
            counter = setInterval(function () {
                if (isRunning) {
                    count++;
                    if (count === 1800) {
                        showMessageDialog(
                            "label.login.loginAgain",
                            "warning",
                            function () {
                                window.location = getContextRoot() + "/account/json/Logout";
                            });
                        isRunning = false;
                    }
                }
            }, 1000);
        }

        interval();
        $(document).on("keydown click mousemove", document, function () {
            clearInterval(counter);
            count = 0;
            interval();
        });
    }
    var menu_selector = "header nav";

    function onScroll() {
        var scroll_top = $(document).scrollTop();
        $(menu_selector + " a").each(function () {
            var hash = $(this).attr("href");
            var target = $(hash);
            if (target.position().top - 78 <= scroll_top && target.position().top + target.outerHeight() > scroll_top) {
                $(menu_selector + " a.active").removeClass("active");
                $(this).addClass("active");
            } else {
                $(this).removeClass("active");
            }
        });
    }

    $(document).on("scroll", onScroll);
    $("header nav a").click(function (e) {
        e.preventDefault();
        $(document).off("scroll");
        $(menu_selector + " a.active").removeClass("active");
        $(this).addClass("active");
        var hash = $(this).attr("href");
        var target = $(hash);
        $("html, body").animate({
            scrollTop: target.offset().top - 78
        }, 500, function () {
        });
    });
    $(document).on("click", '.logo', function () {
        $("html, body").animate({
            scrollTop: 0
        }, 500, function () {
        });
    });
    $(window).scroll(function () {
        if ($(this).scrollTop() !== 0) {
            $('.button_up').fadeIn();
        } else {
            $('.button_up').fadeOut();
        }
    });
    $(document).on("click", '.button_up', function () {
        $('body,html').animate({scrollTop: 0}, 800);
    });
    $(".question-close").click(function () {
        $(this).parent().parent().find(".body-answer").slideToggle();
        $(this).toggleClass("question-open");
    });

    $.ajax(getContextRoot() + "/alert/json/Get", {
        type: "GET",
        success: function (answer) {
            if (typeof answer === 'string') {
                answer = JSON.parse(answer);
            }
            if (answer.data.lotCount > 0) {
            	const element = $("#lot-alerts")[0];
				element.innerText = answer.data.lotCount;
				element.style.display = '';
				if (document.location.toString().indexOf("MyLots") > 0) {
                    showMessageDialog(
                        "label.lot.expired",
                        "warning",
                        () => {}
                    );
                    $.ajax(getContextRoot() + "/alert/json/reset/lots", {
                        type: "PUT",
                        dataType: "json",
                    });
                }
			}
			if (answer.data.tenderCount > 0) {
				const element = $("#tender-alerts")[0];
				element.innerText = answer.data.tenderCount;
				element.style.display = '';
                if (document.location.toString().indexOf("MyTenders") > 0) {
                    showMessageDialog(
                        "label.tender.expired",
                        "warning",
                        () => {
                        }
                    );
                    $.ajax(getContextRoot() + "/alert/json/reset/tenders", {
                        type: "PUT",
                        dataType: "json",
                    });
                }
			}
			if (answer.data.canceledDeal > 0) {
				const element = $("#deal-alerts")[0];
				element.innerText = answer.data.canceledDeal;
				element.style.display = '';
				if (document.location.toString().indexOf("MyDeals") > 0) {
                    $.ajax(getContextRoot() + "/alert/json/reset/deals", {
                        type: "PUT",
                        dataType: "json",
                    });
                }
			}
			if (answer.data.dealOnApprove > 0) {
				const element = $("#deal-on-approve")[0];
				element.innerText = answer.data.dealOnApprove;
				element.style.display = '';
			}
        },
        dataType: "json"
    })
});
