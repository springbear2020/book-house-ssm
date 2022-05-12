$(function () {
    /* ====================================================== Commons =============================================== */
    // Context path and notice modal
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var INFO_CODE = 0;
    var SUCCESS_CODE = 1;
    var DANGER_CODE = 2;
    var WARNING_CODE = 3;

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

    /* ============================================== Show background =============================================== */
    // Get user all background pictures data
    var BACKGROUND_LIST;
    var LIST_LENGTH = 0;
    var BACKGROUND_ID;

    // Generate a random number in bound
    var randomNumberInBound = function (min, max) {
        // The border is [min,max)
        var range = max - min;
        var rand = Math.random();
        var num = min + Math.floor(rand * range);
        return num;
    }

    /*
     * After page loaded successfully, get user personal background data
     */
    $.ajax({
        url: contextPath + "wallpaper/all",
        type: "get",
        dataType: "json",
        async: false,
        success: function (response) {
            if (SUCCESS_CODE === response.code) {
                BACKGROUND_LIST = response.resultMap.backgroundList;
                LIST_LENGTH = response.resultMap.length;
            } else {
                showNoticeModal(response.code, response.msg);
            }
        },
        error: function () {
            showNoticeModal(DANGER_CODE, "请求壁纸数据失败");
        }
    })

    // Get the background url
    var getImage = function () {
        if (LIST_LENGTH <= 0) {
            return "";
        }
        var num = randomNumberInBound(0, parseInt(LIST_LENGTH));
        BACKGROUND_ID = BACKGROUND_LIST[num].id;
        return BACKGROUND_LIST[num].url;
    };

    // Show the picture and set sentence content of the page
    var changeBackgroundSetHitokoto = function (slideObj, imgLink) {
        slideObj.attr("src", imgLink);
        slideObj.parent("a").attr("href", imgLink);
    };

    /* ========================================= First time load background ========================================= */
    // First time load the first image
    changeBackgroundSetHitokoto($(".first-slide"), getImage());
    // First time load the second image at the 1s after page load
    var countingTime2 = 1;
    var time2 = setInterval(function () {
        countingTime2--;
        if (countingTime2 <= 0) {
            changeBackgroundSetHitokoto($(".second-slide"), getImage());
            clearInterval(time2);
        }
    }, 1000)
    // First time load the third image at the 2s after page load
    var countingTime3 = 2;
    var time3 = setInterval(function () {
        countingTime3--;
        if (countingTime3 <= 0) {
            changeBackgroundSetHitokoto($(".third-slide"), getImage());
            clearInterval(time3);
        }
    }, 1000)

    /* =============================================== Picture change =============================================== */
    var changeTimeOfImg1 = 0;
    var changeTimeOfImg2 = 5;
    var changeTimeOfImg3 = 10;
    var pastSeconds = 0;
    // Timer to get new img link and sentence
    setInterval(function () {
        pastSeconds++;
        if (pastSeconds - changeTimeOfImg1 === 15) {
            changeBackgroundSetHitokoto($(".first-slide"), getImage());
            changeTimeOfImg1 = pastSeconds;
        } else if (pastSeconds - changeTimeOfImg2 === 15) {
            changeBackgroundSetHitokoto($(".second-slide"), getImage());
            changeTimeOfImg2 = pastSeconds;
        } else if (pastSeconds - changeTimeOfImg3 === 15) {
            changeBackgroundSetHitokoto($(".third-slide"), getImage());
            changeTimeOfImg3 = pastSeconds;
        }
    }, 1000);
});