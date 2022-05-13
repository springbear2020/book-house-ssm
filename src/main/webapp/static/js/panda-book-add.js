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

    /* ============================================== File upload commons =========================================== */
    // Display the process bar dynamically
    var showUploadProcess = function (rate) {
        $("#div-file-upload-modal").modal({
            backdrop: "static"
        });
        var $process = $(".progress-bar-striped");
        $process.attr("aria-valuenow", rate);
        $process.attr("style", "width: " + rate + "%");
        $process.text(rate + "%");
    };

    // Clear the process bar style and content
    var clearProcess = function () {
        var $process = $(".progress-bar-striped");
        $process.attr("aria-valuenow", "0");
        $process.attr("style", "width: 0");
        $process.text("");
    };

    // File upload service by Qiniu Cloud
    var isPdfUploaded = false;
    var isCoverUploaded = false;
    var qiniuFileUpload = function (file, key, token, mimeType) {
        var putExtra = {
            fname: {key},
            params: {},
            mimeType: [mimeType]
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
                if ("application/pdf" === mimeType) {
                    showUploadProcess(rate.substring(0, rate.indexOf(".") + 3));
                }
            },
            error() {
                if ("application/pdf" === mimeType) {
                    isPdfUploaded = false;
                    showNoticeModal(DANGER_CODE, "图书文件上传失败");
                } else {
                    isCoverUploaded = false;
                    showNoticeModal(DANGER_CODE, "封面文件上传失败");
                }
            },
            complete() {
                // Process stay at 100%
                if ("application/pdf" === mimeType) {
                    isPdfUploaded = true;
                    showUploadProcess(100);
                    $("#div-file-upload-modal").modal('hide');
                    showNoticeModal(SUCCESS_CODE, "图书文件上传成功，感谢您的共享");
                } else {
                    isCoverUploaded = true;
                    showNoticeModal(SUCCESS_CODE, "封面文件上传成功，感谢您的共享");
                }
            }
        }
        // Start upload
        observable.subscribe(observer);
    };

    /* ============================================== PDF upload ==================================================== */
    var bookFile;
    var bookSuffix;
    // Book file content changed event
    $("#input-book-pdf").on('change', function (e) {
        bookFile = e.target.files[0];
        var fileName = $("#input-book-pdf").val();
        bookSuffix = fileName.substring(fileName.lastIndexOf("."));
    });

    // Upload pdf click event
    var bookPath = "";
    var bookUploadId = -1;
    $("#btn-upload-pdf").click(function () {
        if (".pdf" !== bookSuffix) {
            showNoticeModal(WARNING_CODE, "请先选择 PDF 图书文件");
            return false;
        }
        $(this).attr("disabled", "true");
        $("#input-book-pdf").attr("disabled", "true");
        $.ajax({
            url: contextPath + "transfer/upload/book",
            dataType: "json",
            type: "post",
            async: false,
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    qiniuFileUpload(bookFile, response.resultMap.key, response.resultMap.token, "application/pdf");
                    bookPath = response.resultMap.bookPath;
                    bookUploadId = response.resultMap.bookUploadId;
                } else {
                    showNoticeModal(response.code, response.msg);
                    isPdfUploaded = false;
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求上传图书文件失败");
                isPdfUploaded = false;
            }
        })
    });

    /* =============================================== Cover upload ================================================= */
    var coverFile;
    var coverSuffix;
    // Cover file content changed event
    $("#input-book-cover").on('change', function (e) {
        coverFile = e.target.files[0];
        var fileName = $("#input-book-cover").val();
        coverSuffix = fileName.substring(fileName.lastIndexOf("."));
    });

    // Upload cover click event
    var coverPath = "";
    var coverUploadId = -1;
    $("#btn-upload-cover").click(function () {
        if (!(".jpg" === coverSuffix || ".png" === coverSuffix)) {
            showNoticeModal(WARNING_CODE, "请先选择图书封面文件");
            return false;
        }
        $(this).attr("disabled", "true");
        $("#input-book-cover").attr("disabled", "true");
        $.ajax({
            url: contextPath + "transfer/upload/image/1",
            dataType: "json",
            type: "post",
            async: false,
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    qiniuFileUpload(coverFile, response.resultMap.key, response.resultMap.token, "image/*");
                    coverPath = response.resultMap.imgPath;
                    coverUploadId = response.resultMap.imageUploadId;
                } else {
                    showNoticeModal(response.code, response.msg);
                    isCoverUploaded = false;
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求上传图片文件失败");
                isCoverUploaded = false;
            }
        })
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
        isPdfUploaded = false;
        isCoverUploaded = false;
        $("#input-book-pdf").attr("disabled", false);
        $("#input-book-cover").attr("disabled", false);
        $("#btn-upload-pdf").attr("disabled", false);
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

    // Save book click event
    $("#btn-save-book").click(function () {
        if (!isPdfUploaded) {
            showNoticeModal(WARNING_CODE, "请先上传 PDF 图书文件");
            return false;
        }
        if (!isCoverUploaded) {
            showNoticeModal(WARNING_CODE, "请先上传图书封面文件");
            return false;
        }
        if ($("#input-book-title").val().length <= 0) {
            showFormItemValidation($("#input-book-title"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-title"), STATUS_SUCCESS);
        }
        if ($("#input-book-author").val().length <= 0) {
            showFormItemValidation($("#input-book-author"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-author"), STATUS_SUCCESS);
        }
        if ($("#input-book-comments").val().length <= 0) {
            showFormItemValidation($("#input-book-comments"), STATUS_ERROR);
            return false;
        } else {
            showFormItemValidation($("#input-book-comments"), STATUS_SUCCESS);
        }

        // var comments = $("#input-book-comments").val();

        // Ask server to save book record
        $.ajax({
            url: contextPath + "book",
            type: "post",
            dataType: "json",
            data: "bookUploadId=" + bookUploadId + "&coverUploadId=" + coverUploadId + "&bookPath=" + bookPath + "&coverPath=" + coverPath + "&" + $("#form-save-book").serialize(),
            success: function (response) {
                showNoticeModal(response.code, response.msg);
                resetBookSaveFormItem();
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求新增图书记录失败");
            }
        });
    });
});