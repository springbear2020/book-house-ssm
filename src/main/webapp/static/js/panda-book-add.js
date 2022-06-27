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

    /* =========================================== Search book by title ============================================= */
    $("#btn-upload-book-search").click(function () {
        var title = $("#input-book-title").val();
        if (title.length <= 0) {
            showNoticeModal(WARNING_CODE, "请输入书名");
            return false;
        }
        window.open("https://search.douban.com/book/subject_search?search_text=" + title);
    });

    /* ========================================= Save book record =================================================== */
    // Validation type
    var STATUS_SUCCESS = "success";
    var STATUS_WARNING = "warning"
    var STATUS_ERROR = "error";

    // Show validate msg of the form item
    var showFormItemValidation = function (element, status) {
        // Reset the existing style and clear the notice msg
        element.parent().removeClass("has-success has-error has-warning");
        element.next("span").removeClass("glyphicon-ok glyphicon-remove glyphicon-warning-sign");
        if (STATUS_ERROR === status) {
            element.parent().addClass("has-error");
            element.next("span").addClass("glyphicon-remove");
        } else if (STATUS_WARNING === status) {
            element.parent().addClass("has-warning");
            element.next("span").addClass("glyphicon-warning-sign");
        }
    };

    // Reset the upload form item
    var resetBookSaveFormItem = function () {
        $("#input-book-cover").attr("disabled", false);
        $("#btn-upload-cover").attr("disabled", false);
        $("#input-book-title").val("");
        $("#input-book-author").val("");
        $("#input-book-translator").val("");
        $("#input-book-comments").val("");
        showFormItemValidation($("#input-book-title"), SUCCESS_CODE);
        showFormItemValidation($("#input-book-author"), SUCCESS_CODE);
        showFormItemValidation($("#input-book-translator"), SUCCESS_CODE);
        showFormItemValidation($("#input-book-author"), SUCCESS_CODE);
    };

    var coverFile;
    var coverSuffix;
    // Cover file content changed event
    $("#input-book-cover").on('change', function (e) {
        coverFile = e.target.files[0];
        var fileName = $("#input-book-cover").val();
        coverSuffix = fileName.substring(fileName.lastIndexOf("."));
    });

    // Save book click event
    $("#save-book").click(function () {
        // Book title
        var title = $("#input-book-title").val();
        if (title.length <= 0) {
            alert()
            showFormItemValidation($("#input-book-title"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-title"), STATUS_SUCCESS);
        }
        // Book cover
        if (!(".jpg" === coverSuffix || ".png" === coverSuffix)) {
            showNoticeModal(WARNING_CODE, "请先选择图书封面文件");
            return false;
        }
        // Book author
        var author = $("#input-book-author").val();
        if (author.length <= 0) {
            showFormItemValidation($("#input-book-author"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-author"), STATUS_SUCCESS);
        }
        var comments = $("#input-book-comments").val();
        if (comments.length <= 0) {
            showFormItemValidation($("#input-book-comments"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-comments"), STATUS_SUCCESS);
        }

        // 获取表单中的文件
        var translator = $("#input-book-translator").val();
        var formData = new FormData();
        formData.append("coverFile", coverFile);
        formData.append("title", title);
        formData.append("author", author);
        formData.append("translator", translator);
        formData.append("comments", comments);

        $.ajax({
            url: contextPath + "book",
            type: "post",
            dataType: "json",
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (response) {
                showNoticeModal(response.code, response.msg);
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求新增图书失败");
            }
        })
    });
});