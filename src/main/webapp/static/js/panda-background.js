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
    var IMAGE;

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

    var getImage = function () {
        var num = randomNum(4, 9);
        IMAGE = num + ".jpg";
        return IMAGE;
    };

    var randomNum = function (minNum, maxNum) {
        switch (arguments.length) {
            case 1:
                return parseInt(Math.random() * minNum + 1, 10);
                break;
            case 2:
                return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10);
                break;
            default:
                return 0;
                break;
        }
    }

    // Show the picture and set character content of the page
    var changeBackgroundSetHitokoto = function (slideObj, imgLink, sentence) {
        slideObj.attr("src", imgLink);
        slideObj.parent("a").attr("href", imgLink);
        slideObj.parent().parent().find("h4").text(sentence);
    };

    // First time load the first image
    changeBackgroundSetHitokoto($(".first-slide"), getImage(), getHitokoto());
    // First time load the second image
    var countingTime2 = 1;
    var time2 = setInterval(function () {
        countingTime2--;
        if (countingTime2 <= 0) {
            changeBackgroundSetHitokoto($(".second-slide"), getImage(), getHitokoto());
            clearInterval(time2);
        }
    }, 1000)
    // First time load the third image
    var countingTime3 = 2;
    var time3 = setInterval(function () {
        countingTime3--;
        if (countingTime3 <= 0) {
            changeBackgroundSetHitokoto($(".third-slide"), getImage(), getHitokoto());
            clearInterval(time3);
        }
    }, 1000)

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

    // Display the process bar dynamically
    var  show_process = function (rate) {
        $(".progress").attr("style", "display: block");
        var $process_bar = $(".progress-bar-striped");
        $process_bar.attr("aria-valuenow", rate);
        $process_bar.attr("style", "width: " + rate + "%");
        $process_bar.text(rate + "%");
    };

    // Close the upload modal event
    $("#btn-background-upload-modal-close").click(function () {
        // Hide the start upload button and the process bar
        var $process_bar = $(".progress-bar-striped");
        $process_bar.attr("aria-valuenow", 0);
        $process_bar.attr("style", "width: 0%");
        $process_bar.text("");
        $process_bar.parent().attr("style", "display: none");
        $("#div-book-choose-upload").attr("style", "display: none");
    });

    // File upload service by Qiniu Cloud
    var qiniu_upload_book = function (file, key, token) {
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
                console.log(rate);
                show_process(rate.substring(0, rate.indexOf(".") + 3));
            },
            error() {
                showNoticeModal(DANGER_CODE, "图片上传失败，请稍后重试");
            },
            complete() {
                showNoticeModal(SUCCESS_CODE, "背景图片上传成功，感谢您的共享");
                // Process stay at 100%
                show_process(100);
            }
        }

        // Start upload
        observable.subscribe(observer);
    };

    // Listening the content change of the file choose input element
    $("#input-background-upload").on("change", function (e) {
        var file = e.target.files[0];
        var srcFileName = $("#input-background-upload").val();
        var suffix = srcFileName.substring(srcFileName.lastIndexOf("."));
        // Display the upload now button
        var $btnBackgroundStartUpload = $("#btn-background-start-upload");
        $(".progress").attr("style", "display: none");
        $btnBackgroundStartUpload.parent().attr("style", "display: block");
        // Start uploading book button event
        $btnBackgroundStartUpload.click(function () {
            if (!(".png" === suffix || ".jpg" === suffix)) {
                showNoticeModal(WARNING_CODE, "请选择图片文件")
                return false;
            }
            $.ajax({
                url: contextPath + "transfer/upload/image/2",
                dataType: "json",
                type: "post",
                success: function (response) {
                    if (SUCCESS_CODE === response.code) {
                        $("#div-book-choose-upload").attr("style", "display: none");
                        qiniu_upload_book(file, response.resultMap.key, response.resultMap.token);
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
});