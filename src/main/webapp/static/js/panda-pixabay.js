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

    /* ================================================ Show pixabay ================================================ */
    var SENTENCE;
    var IMAGE;
    var PIXABAY;

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

    var getPixabay = function () {
        $.ajax({
            url: contextPath + "pixabay/first",
            dataType: "json",
            type: "get",
            async: false,
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    PIXABAY = response.resultMap.pixabayList[0];
                    IMAGE = PIXABAY.url;
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求获取 Pixabay 数据失败");
            }
        })
        return IMAGE;
    };

    // Show the picture and set character content of the page
    var changeBackgroundSetHitokoto = function (slideObj, imgLink, sentence) {
        slideObj.attr("src", imgLink);
        slideObj.parent("a").attr("href", imgLink);
        slideObj.parent().parent().find("h4").text(sentence);
    };

    // After page load successfully, get the first pixabay
    changeBackgroundSetHitokoto($(".first-slide"), getPixabay(), getHitokoto());

    /* ================================================ Delete pixabay ============================================== */
    $("#btn-pixabay-delete").click(function () {
        // Send a request to server for delete a pixabay picture
        $.ajax({
            url: contextPath + "pixabay/" + PIXABAY.id,
            type: "post",
            data: "_method=delete",
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    changeBackgroundSetHitokoto($(".first-slide"), getPixabay(), getHitokoto());
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求删除 Pixabay 记录失败");
            }
        })
    });

    /* ====================================== Clear all pixabay then insert new ===================================== */
    // Open the pixabay modal
    $("#btn-pixabay-open-modal").click(function () {
        $("#modal-get-pixabay").modal({
            backdrop: "static"
        })
    });

    // Get pixabay by condition and pages click event
    $("#btn-pixabay-add").click(function () {
        var $keywords = $("#input-pixabay-condition");
        var condition = $keywords.val();
        var pages = $("#select-pixabay-pages").val();
        $keywords.parent().removeClass("has-error has-success has-warning");
        // The filter condition can not be empty
        if (condition.length <= 0) {
            $keywords.parent().addClass("has-error");
            return false;
        } else {
            $keywords.parent().addClass("has-success");
        }
        // Send an ajax request to server to add new pixabay though python spider
        $.ajax({
            url: contextPath + "pixabay/" + condition + "/" + pages,
            type: "post",
            data: "_method=put",
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    changeBackgroundSetHitokoto($(".first-slide"), getPixabay(), getHitokoto());
                }
                showNoticeModal(response.code, response.msg);
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求新增 Pixabay 记录失败");
            }
        })
    });

    // Close get pixabay modal click event
    $("#btn-pixabay-modal-close").click(function () {
        var $input_pixabay_condition = $("#input-pixabay-condition");
        $input_pixabay_condition.parent().removeClass("has-error has-success has-warning");
        $input_pixabay_condition.val("");
        $("#select-pixabay-pages").val("01");
    });
});