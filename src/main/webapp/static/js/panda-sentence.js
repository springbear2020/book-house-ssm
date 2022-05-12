$(function () {
    /* ====================================================== Commons =============================================== */
    // Response code from server
    var INFO_CODE = 0;

    // Show the notice modal
    var showNoticeModal = function (code, msg) {
        var $noticeContent = $("#h-notice-content");
        // Clear the existed style of the notice object
        $noticeContent.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        if (INFO_CODE === code) {
            $noticeContent.parent().addClass("alert-info");
        } else if (SUCCESS_CODE === code) {
            $noticeContent.parent().addClass("alert-success");
        } else if (WARNING_CODE === code) {
            $noticeContent.parent().addClass("alert-warning");
        } else if (DANGER_CODE === code) {
            $noticeContent.parent().addClass("alert-danger");
        }
        $noticeContent.text(msg);
        $("#div-notice-modal").modal('show');
    };

    /* ============================================= Display sentence =============================================== */
    var SENTENCE;
    var AUTHOR;
    var TYPE = 0;

    // Get the poem form the jinrishici.com
    var getPoem = function () {
        jinrishici.load(function (result) {
            SENTENCE = result.data.content;
            AUTHOR = result.data.origin.dynasty + " - " + result.data.origin.author;
        });
    };

    // Get a sentence from the hitokoto.cn
    var getHitokoto = function () {
        $.ajax({
            url: 'https://v1.hitokoto.cn',
            dataType: 'json',
            async: false,
            success(response) {
                SENTENCE = response.hitokoto;
                AUTHOR = response.from;
            }
        })
    };

    // Set the display sentence
    var setSentence = function () {
        $(".cover-heading").text(SENTENCE);
        $(".lead").text(AUTHOR);
    };

    // Change the sentence type
    var changeMode = function () {
        setInterval(function () {
            if (TYPE === 0) {
                getHitokoto();
                setSentence();
            } else if (TYPE ===1) {
                getPoem();
                setSentence();
            }
        }, 6000);
    };

    // After page loaded, set the first sentence
    getHitokoto();
    setSentence();
    changeMode();

    // Change to hitokoto
    $("#hitokoto").click(function () {
        TYPE = 0;
        showNoticeModal(INFO_CODE,"正在切换到一言，请稍等······");
    });

    // Change to poem
    $("#jinrishici").click(function () {
        TYPE = 1;
        showNoticeModal(INFO_CODE,"正在切换到诗词，请稍等······");
    });
});