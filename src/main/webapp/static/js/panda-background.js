$(function () {
    /* ====================================================== Commons =============================================== */
    // Context path and notice modal
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var INFO_CODE = 0;
    var SUCCESS_CODE = 1;
    var DANGER_CODE = 2;
    var WARNING_CODE = 3;

    // Prevent the default submit action of form
    $("form").on("submit", function () {
        return false;
    });

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
    var SENTENCE;
    var getHitokoto = function () {
        $.ajax({
            url: 'https://v1.hitokoto.cn',
            dataType: 'json',
            async: false,
            success(response) {
                SENTENCE = response.hitokoto;
            }
        })
        return SENTENCE;
    };

    // Get user all background pictures data
    var BACKGROUND_LIST;
    var LIST_LENGTH;
    $.ajax({
        url: contextPath + "background/all",
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
            showNoticeModal(DANGER_CODE, "请求个人背景图数据失败");
        }
    })

    var randomNumberInBound = function (min, max) {
        // The border is [min,max)
        var range = max - min;
        var rand = Math.random();
        var num = min + Math.floor(rand * range);
        return num;
    }

    var getImage = function () {
        var num = randomNumberInBound(0, parseInt(LIST_LENGTH));
        return BACKGROUND_LIST[num].url;
    };

    // Show the picture and set sentence content of the page
    var changeBackgroundSetHitokoto = function (slideObj, imgLink, sentence) {
        slideObj.attr("src", imgLink);
        slideObj.parent("a").attr("href", imgLink);
        slideObj.parent().parent().find("h4").text(sentence);
    };

    /* ========================================= First load background ============================================== */
    // First time load the first image
    changeBackgroundSetHitokoto($(".first-slide"), getImage(), getHitokoto());
    // First time load the second image at the 1s after page load
    var countingTime2 = 1;
    var time2 = setInterval(function () {
        countingTime2--;
        if (countingTime2 <= 0) {
            changeBackgroundSetHitokoto($(".second-slide"), getImage(), getHitokoto());
            clearInterval(time2);
        }
    }, 1000)
    // First time load the third image at the 2s after page load
    var countingTime3 = 2;
    var time3 = setInterval(function () {
        countingTime3--;
        if (countingTime3 <= 0) {
            changeBackgroundSetHitokoto($(".third-slide"), getImage(), getHitokoto());
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
            changeBackgroundSetHitokoto($(".first-slide"), getImage(), getHitokoto());
            changeTimeOfImg1 = pastSeconds;
        } else if (pastSeconds - changeTimeOfImg2 === 15) {
            changeBackgroundSetHitokoto($(".second-slide"), getImage(), getHitokoto());
            changeTimeOfImg2 = pastSeconds;
        } else if (pastSeconds - changeTimeOfImg3 === 15) {
            changeBackgroundSetHitokoto($(".third-slide"), getImage(), getHitokoto());
            changeTimeOfImg3 = pastSeconds;
        }
    }, 1000);

    /* ================================================= Background upload ========================================== */
    // Background upload click event
    $("#btn-background-upload").click(function () {
        $("#modal-upload-background").modal({
            backdrop: "static"
        })
    });

    // Close the upload modal event
    $("#btn-background-upload-modal-close").click(function () {
        $("#input-background-upload").attr("disabled", false);
        $("#btn-background-start-upload").attr("disabled", false);
    });

    // File upload service by Qiniu Cloud
    var qiniuCloudFileUpload = function (file, key, token) {
        var putExtra = {
            fname: {key},
            params: {},
            mimeType: ["image/*"]
        }
        var config = {
            shouldUseQiniuFileName: false,
            region: qiniu.region.z2,
            forceDirect: true,
        };

        // Get a upload service obj
        var observable = qiniu.upload(file, key, token, putExtra, config);

        var observer = {
            next(res) {
                // Show the progress of the book upload
                var rate = res.total.percent + "";
            },
            error() {
                showNoticeModal(DANGER_CODE, "背景图片上传失败");
            },
            complete() {
                showNoticeModal(SUCCESS_CODE, "背景图片上传成功，感谢您的共享");
                $("#modal-upload-background").modal('hide');
                $("#input-background-upload").attr("disabled", false);
                $("#btn-background-start-upload").attr("disabled", false);
            }
        }

        // Start upload
        observable.subscribe(observer);
    };

    // Listening the content change of the file choose input element
    var file;
    var suffix;
    $("#input-background-upload").on("change", function (e) {
        file = e.target.files[0];
        var srcFileName = $("#input-background-upload").val();
        suffix = srcFileName.substring(srcFileName.lastIndexOf("."));
    });

    // Start uploading book button event
    $("#btn-background-start-upload").click(function () {
        if (!(".png" === suffix || ".jpg" === suffix)) {
            showNoticeModal(WARNING_CODE, "请选择图片文件")
            return false;
        }
        $(this).attr("disabled", "disabled");
        $("#input-background-upload").attr("disabled", "disabled");
        // Ask server the save image file
        $.ajax({
            url: contextPath + "transfer/upload/image/3",
            dataType: "json",
            type: "post",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    qiniuCloudFileUpload(file, response.resultMap.key, response.resultMap.token);
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求上传背景图片文件失败");
            }
        })
    });
});