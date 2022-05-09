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

    // Info display table
    var $infoDisplayTable = $("#div-info-display-table");

    /* ========================== */
    // Build display upload record module
    var build_book_upload_record_display_module = function (response) {
        var upload = response.resultMap.bookUploadList[0];
        // Title
        $("<h3></h3>").append("上传记录").addClass("sub-header").appendTo($infoDisplayTable);
        // Parent of table
        var $div_table_parent = $("<div></div>").addClass("table-responsive").appendTo($infoDisplayTable);
        var $table = $("<table></table>").addClass("table table-bordered table-hover").appendTo($div_table_parent);
        // Table thead
        var $thead = $("<thead></thead>").appendTo($table);
        var $tr_thead = $("<tr></tr>").appendTo($thead);
        $("<th></th>").append("ID").appendTo($tr_thead);
        $("<th></th>").append("用户编号").appendTo($tr_thead);
        $("<th></th>").append("用户名").appendTo($tr_thead);
        $("<th></th>").append("用户类型").appendTo($tr_thead);
        $("<th></th>").append("上传时间").appendTo($tr_thead);
        $("<th></th>").append("文件类型").appendTo($tr_thead);
        $("<th></th>").append("空间名称").appendTo($tr_thead);
        $("<th></th>").append("记录状态").appendTo($tr_thead);
        $("<th></th>").append("访问地址").appendTo($tr_thead);
        $("<th></th>").append("删除文件").appendTo($tr_thead);
        // Table tbody
        var $tbody = $("<tbody></tbody>").appendTo($table);
        var $tr_tbody = $("<tr></tr>").appendTo($tbody);
        $("<td></td>").append(upload.id).appendTo($tr_tbody);
        $("<td></td>").append(upload.userId).appendTo($tr_tbody);
        $("<td></td>").append(upload.username).appendTo($tr_tbody);
        var userType = upload.userType === 0 ? "普通用户" : "管理员";
        $("<td></td>").append(userType).appendTo($tr_tbody);
        $("<td></td>").append(upload.uploadTime).appendTo($tr_tbody);
        var fileType;
        if (0 === upload.type) {
            fileType = "图书";
        } else if (1 === upload.type) {
            fileType = "封面";
        } else if (2 === upload.type) {
            fileType = "头像";
        } else if (3 === upload.type) {
            fileType = "背景";
        }
        $("<td></td>").append(fileType).appendTo($tr_tbody);
        $("<td></td>").append(upload.bucket).appendTo($tr_tbody);
        var status = upload.status === 0 ? "已处理" : "未处理";
        $("<td></td>").append(status).appendTo($tr_tbody);
        // File link element
        var $link = $("<td></td>").appendTo($tr_tbody);
        var $a_link = $("<a></a>").attr("target", "_blank").attr("href", upload.domain + upload.key)
            .appendTo($link);
        $("<span></span>").addClass("glyphicon glyphicon-eye-open").attr("aria-hidden", "true")
            .appendTo($a_link);
        // File delete element
        var $delete = $("<td></td>").appendTo($tr_tbody);
        var $a_delete = $("<a></a>").attr("role", "button").attr("id", "btn-delete-bucket-file").appendTo($delete);
        $("<span></span>").addClass("glyphicon glyphicon-trash").attr("aria-hidden", "true").appendTo($a_delete);

        // Delete file click event
        $("#btn-delete-bucket-file").click(function () {
            // @DeleteMapping("/transfer/upload/{id}")
            $.ajax({
                url: contextPath + "transfer/upload/" + upload.id,
                type: "post",
                data: "_method=delete",
                dataType: "json",
                success: function (response) {
                    if (SUCCESS_CODE === response.code) {
                        $infoDisplayTable.empty();
                        get_book_upload_record();
                    }
                    show_notice_modal(response.code, response.msg);
                },
                error: function () {
                    show_notice_modal(DANGER_CODE, "请求删除图书文件失败");
                }
            })
        });
    };
});