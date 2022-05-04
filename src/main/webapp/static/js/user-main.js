$(function () {
    /* ====================================================== Commons =============================================== */
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var INFO_CODE = 0;
    var SUCCESS_CODE = 1;
    var WARNING_CODE = 2;
    var DANGER_CODE = 3;

    // Prevent the default submit action of form
    $("form").on("submit", function () {
        return false;
    });

    // Show the notice modal
    var show_notice_modal = function (responseCode, msg) {
        var $modalObj = $("#div-modal-notice");
        var $noticeObj = $("#h-notice-content");
        // Clear the existed style of the notice object
        $noticeObj.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        if (INFO_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-info");
        } else if (SUCCESS_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-success");
        } else if (WARNING_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-warning");
        } else if (DANGER_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-danger");
        }
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    /* ================================================== Upload Book =============================================== */
    // Upload book link to open the upload modal
    $("#link-upload-book").click(function () {
        // Can not close it unless click close symbol
        $("#modal-upload-book").modal({
            backdrop: "static"
        });
    });

    // Listening the content change of the file choose input element
    $("#input-book-upload").on("change", function (e) {
        var file = e.target.files[0];
        var srcFileName = $("#input-book-upload").val();
        var suffix = srcFileName.substring(srcFileName.lastIndexOf("."));
        // Display the upload now button
        var $btn_book_start_upload = $("#btn-book-start-upload");
        $btn_book_start_upload.attr("style", "display: block");
        // Start uploading book button event
        $btn_book_start_upload.click(function () {
            if (".pdf" !== suffix) {
                show_notice_modal(WARNING_CODE, "请选择 PDF 图书文件")
                return false;
            }
            // Send an ajax request to server for getting qiniu token
            // 0 - Upload pdf book file
            $.ajax({
                url: contextPath + "upload/0",
                dataType: "json",
                success: function (response) {
                    if (SUCCESS_CODE === response.code) {
                        $("#div-book-choose-upload").attr("style", "display: none");
                        qiniu_upload(file, response.resultMap.content[1], response.resultMap.content[0]);
                    } else {
                        show_notice_modal(response.code, response.msg);
                    }
                }
            })
        });
    });

    // File upload service by Qiniu Cloud
    var qiniu_upload = function (file, token, key) {
        // Config info
        var putExtra = {
            fname: {key},
            params: {},
            mimeType: ["application/pdf"]
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
                show_process(rate.substring(0, rate.indexOf(".") + 3));
            },
            error() {
                show_notice_modal(DANGER_CODE, "图书上传失败，请稍后重试");
            },
            complete() {
                show_notice_modal(SUCCESS_CODE, "图书上传成功，感谢您的共享");
                // Process stay at 100%
                show_process(100);
            }
        }

        // Start upload
        observable.subscribe(observer);
    };

    // Display the process bar dynamically
    var show_process = function (rate) {
        $(".progress").attr("style", "display: block");
        var $process_bar = $(".progress-bar-striped");
        $process_bar.attr("aria-valuenow", rate);
        $process_bar.attr("style", "width: " + rate + "%");
        $process_bar.text(rate + "%");
    };

    // Close the upload modal event
    $("#btn-book-upload-modal-close").click(function () {
        // Hide the start upload button and the process bar
        $(".progress").attr("style", "display: none");
        $("#div-book-choose-upload").attr("style", "display: none");
        // Clear the content of the input file element
        $("#input-book-upload").val("");
    });
});