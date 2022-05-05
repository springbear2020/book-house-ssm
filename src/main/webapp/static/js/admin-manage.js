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

    // Admin manage page main content element
    var $div_main_content = $("#div-admin-manage-content");

    // File upload service by Qiniu Cloud
    var qiniu_upload_image = function (file, key, token) {
        // Config info
        var putExtra = {
            fname: {key},
            params: {},
            mimeType: ["image*"]
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
                console.log(rate.substring(0, rate.indexOf(".") + 3));
            },
            error() {
                show_notice_modal(DANGER_CODE, "图书上传失败，请稍后重试");
            },
            complete() {
                show_notice_modal(SUCCESS_CODE, "图书上传成功，感谢您的共享");
            }
        }

        // Start upload
        observable.subscribe(observer);
    };

    /* ============================================== Upload background ============================================= */
    // Upload background link click event
    $("#link-upload-background").click(function () {
        $div_main_content.empty();
        build_background_upload();
    });

    // Build upload background image module
    var build_background_upload = function () {
        $("<h3></h3>").append("背景上传").addClass("page-header").appendTo($div_main_content);
        // Choose file input element
        var $input = $("<input/>").attr("type", "file").attr("accept", "image/jpeg,image/png")
            .attr("id", "input-background-upload").addClass("btn btn-block btn-success").appendTo($div_main_content);
        // Dividing line
        $("<hr/>").appendTo($div_main_content);
        // Background picture img element
        var $img = $("<img/>").attr("src", "").attr("alt", "").attr("id", "img-background")
            .addClass("img-responsive").appendTo($div_main_content);
        // Dividing line
        $("<hr/>").appendTo($div_main_content);
        // Upload now button element
        var $btn = $("<button></button>").attr("type", "button").attr("id", "btn-background-upload")
            .append("立即上传").addClass("btn btn-block btn-primary").appendTo($div_main_content);

        var file;
        var fileName;
        // Choose image input element content change event
        $input.on("change", function (e) {
            fileName = $(this).val();
            file = e.target.files[0];
            // Get the file real disk path to display it in img element
            var path = window.URL.createObjectURL(this.files[0]);
            $img.attr("src", path);
        });

        // Upload image now click event
        $btn.click(function () {
            var suffix = fileName.substring(fileName.lastIndexOf("."));
            // Only the image file can be uploaded
            if (!(suffix === ".jpg" || suffix === ".png" || suffix === ".jpeg")) {
                show_notice_modal(WARNING_CODE, "请选择正确格式的图片文件");
                return false;
            }
            // Ask the server to save the background image file
            $.ajax({
                url: contextPath + "transfer/upload/image/3",
                type: "post",
                dataType: "json",
                success: function (response) {
                    if (SUCCESS_CODE === response.code) {
                        qiniu_upload_image(file, response.resultMap.key, response.resultMap.token);
                    } else {
                        show_notice_modal(response.code, response.msg);
                    }
                },
                error: function () {
                    show_notice_modal(DANGER_CODE, "请求上传图片文件失败");
                }
            })
        });
    };

    /* ================================================ Show pixabay ================================================= */
    // Pixabay link click event
    $("#link-show-pixabay").click(function () {
        $div_main_content.empty();
        get_pixabay();
    });

    // Send an ajax request to server for getting a pixabay picture info
    var get_pixabay = function () {
        $div_main_content.empty();
        // Send an ajax request to server for pixabay info
        $.ajax({
            url: contextPath + "pixabay",
            type: "get",
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    build_pixabay_show_module(response);
                } else {
                    build_pixabay_add_button_only();
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(DANGER_CODE, "请求获取 Pixabay 图片失败");
            }
        })
    };

    // Build show pixabay module
    var build_pixabay_show_module = function (response) {
        var pixabay = response.resultMap.pixabayList[0];
        $("<h3></h3>").append("Pixabay").addClass("page-header").appendTo($div_main_content);
        var $div_pixabay_manage_parent = $("<div></div>").addClass("text-center").appendTo($div_main_content);
        // Delete current picture link
        var $span_delete = $("<span></span>").addClass("glyphicon glyphicon-trash").attr("aria-hidden", "true");
        var $btn_delete = $("<button></button>").attr("type", "button").addClass("btn btn-lg btn-primary")
            .append($span_delete).appendTo($div_pixabay_manage_parent);
        // Delete all pictures link then add new
        var $span_refresh = $("<span></span>").addClass("glyphicon glyphicon-refresh").attr("aria-hidden", "true");
        var $btn_refresh = $("<button></button>").attr("type", "button").addClass("btn btn-lg btn-warning")
            .append($span_refresh).appendTo($div_pixabay_manage_parent);
        // Dividing line
        $("<hr/>").appendTo($div_main_content);
        // Display the img
        var $a_img = $("<a></a>").attr("target", "_blank").attr("href", pixabay.url).appendTo($div_main_content);
        $("<img/>").attr("src", "" + pixabay.url).attr("alt", "").addClass("img-responsive").appendTo($a_img);

        // Delete a pixabay picture click event
        $btn_delete.click(function () {
            // Send a request to server for delete a pixabay picture
            $.ajax({
                url: contextPath + "pixabay/" + pixabay.id,
                type: "post",
                data: "_method=delete",
                dataType: "json",
                success: function (response) {
                    if (SUCCESS_CODE === response.code) {
                        // Delete the current pixabay successfully, display the next pixabay
                        get_pixabay();
                    } else {
                        show_notice_modal(response.code, response.msg);
                    }
                },
                error: function () {
                    show_notice_modal(DANGER_CODE, "请求删除 Pixabay 图片失败");
                }
            })
        });

        // Delete all pictures than add new pixabay pictures
        $btn_refresh.click(function () {
            // Show the add pixabay modal
            $("#modal-get-pixabay").modal({
                backdrop: "static"
            })
        });
    };

    // Build the add new pixabay button only to fix the problem when no record in the t_pixabay table,
    // display nothing in the pixabay manage page
    var build_pixabay_add_button_only = function () {
        var $div_pixabay_manage_parent = $("<div></div>").addClass("text-center").appendTo($div_main_content);
        // Delete all pictures link then add new
        var $span_refresh = $("<span></span>").addClass("glyphicon glyphicon-refresh").attr("aria-hidden", "true");
        var $btn_refresh = $("<button></button>").attr("type", "button").addClass("btn btn-lg btn-warning")
            .append($span_refresh).appendTo($div_pixabay_manage_parent);

        // Delete all pictures than add new pixabay pictures
        $btn_refresh.click(function () {
            // Show the add pixabay modal
            $("#modal-get-pixabay").modal({
                backdrop: "static"
            })
        });
    };

    // Get pixabay by condition and pages click event
    $("#btn-pixabay-ok").click(function () {
        var $input_condition = $("#input-pixabay-condition");
        var condition = $input_condition.val();
        var pages = $("#select-pixabay-pages").val();
        $input_condition.parent().removeClass("has-error has-success has-warning");
        // The filter condition can not be empty
        if (condition.length <= 0) {
            $input_condition.parent().addClass("has-error");
            return false;
        } else {
            $input_condition.parent().addClass("has-success");
        }
        // Send an ajax request to server to add new pixabay though python spider
        $.ajax({
            url: contextPath + "pixabay/" + condition + "/" + pages,
            type: "post",
            data: "_method=put",
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    get_pixabay();
                    $("#modal-get-pixabay").modal('hide');
                }
                show_notice_modal(response.code, response.msg);
            },
            error: function () {
                show_notice_modal(DANGER_CODE, "请求获取 Pixabay 数据失败");
            }
        })
    });

    // Close get pixabay modal click event
    $("#btn-pixabay-modal-close").click(function () {
        var $input_pixabay_condition = $("#input-pixabay-condition");
        $input_pixabay_condition.parent().removeClass("has-error has-success  has-warning");
        $input_pixabay_condition.val("");
        $("#select-pixabay-pages").val(1);
    });

    /* ================================================ Show portrait ================================================= */
    $("#link-show-portrait").click(function () {
        $div_main_content.empty();
        show_user_portrait_module();
    });

    // Build show user portrait
    var show_user_portrait_module = function () {
        $("<h3></h3>").append("用户头像").addClass("page-header").appendTo($div_main_content);
        var $div_row = $("<div></div>").addClass("row placeholders").appendTo($div_main_content);

        // First portrait
        var $div_img_parent = $("<div></div>").addClass("col-xs-6 col-sm-3 placeholder").appendTo($div_row);
        var $img = $("<img/>").attr("width", "200").attr("height", "200").attr("alt", "")
            .addClass("img-responsive").attr("src", "3.jpg").appendTo($div_img_parent);
        $("<h4></h4>").append("Spring-_-Bear").appendTo($div_img_parent);
    };

    /* ================================================ Upload book ================================================= */
    $("#link-upload-book").click(function () {
        $div_main_content.empty();
        get_book_upload_record();
    });

    // Get the first unprocessed book upload record in the table
    var get_book_upload_record = function () {
        $.ajax({
            url: contextPath + "record/upload/book",
            dataType: "json",
            type: "get",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    build_book_upload_record_display_module(response);
                    build_book_upload_form_module(response);
                } else {
                    show_notice_modal(response.code, response.msg);
                }
            },
            error: function () {
                show_notice_modal(DANGER_CODE, "请求获取图书上传记录失败");
            }
        })
    };

    // Build display upload record module
    var build_book_upload_record_display_module = function (response) {
        var upload = response.resultMap.bookUploadList[0];
        // Title
        $("<h3></h3>").append("图书上传").addClass("sub-header").appendTo($div_main_content);
        // Parent of table
        var $div_table_parent = $("<div></div>").addClass("table-responsive").appendTo($div_main_content);
        var $table = $("<table></table>").addClass("table table-bordered").appendTo($div_table_parent);
        // Table thead
        var $thead = $("<thead></thead>").appendTo($table);
        var $tr_thead = $("<tr></tr>").appendTo($thead);
        $("<th></th>").append("#").appendTo($tr_thead);
        $("<th></th>").append("用户 id").appendTo($tr_thead);
        $("<th></th>").append("用户名").appendTo($tr_thead);
        $("<th></th>").append("用户类型").appendTo($tr_thead);
        $("<th></th>").append("上传时间").appendTo($tr_thead);
        $("<th></th>").append("文件类型").appendTo($tr_thead);
        $("<th></th>").append("空间名称").appendTo($tr_thead);
        $("<th></th>").append("访问地址").appendTo($tr_thead);
        $("<th></th>").append("移动文件").appendTo($tr_thead);
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
        // File link element
        var $link = $("<td></td>").appendTo($tr_tbody);
        var $a_link = $("<a></a>").attr("target", "_blank").attr("href", upload.domain + upload.key)
            .appendTo($link);
        $("<span></span>").addClass("glyphicon glyphicon-eye-open").attr("aria-hidden", "true")
            .appendTo($a_link);
        // File move element
        var $move = $("<td></td>").appendTo($tr_tbody);
        var $a_move = $("<a></a>").attr("role", "button").appendTo($move);
        $("<span></span>").addClass("glyphicon glyphicon-move").attr("aria-hidden", "true").appendTo($a_move);
        // File delete element
        var $delete = $("<td></td>").appendTo($tr_tbody);
        var $a_delete = $("<a></a>").attr("role", "button").appendTo($delete);
        $("<span></span>").addClass("glyphicon glyphicon-trash").attr("aria-hidden", "true").appendTo($a_delete);
    };

    // Build upload book form module
    var build_book_upload_form_module = function (response) {
        var upload = response.resultMap.bookUploadList[0];
        var $form = $("<form></form>").addClass("form-group").appendTo($div_main_content);
        // Div 1
        var $div1 = $("<div></div>").addClass("form-group form-inline").appendTo($form);
        // User id
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-user-id").appendTo($div1);
        $("<input/>").attr("type", "text").addClass("form-control").attr("disabled", "disabled")
            .attr("id", "input-book-user-id").val(upload.userId).appendTo($div1);
        // Username
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-username").appendTo($div1);
        $("<input/>").attr("type", "text").addClass("form-control").attr("disabled", "disabled")
            .attr("id", "input-book-username").val(upload.username).appendTo($div1);
        // Upload time
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-upload-time").appendTo($div1);
        $("<input/>").attr("type", "text").addClass("form-control").attr("disabled", "disabled")
            .attr("id", "input-book-upload-time").val(upload.uploadTime).appendTo($div1);
        // Book path
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-path").appendTo($div1);
        $("<input/>").attr("type", "text").addClass("form-control").attr("disabled", "disabled")
            .attr("id", "input-book-path").val(upload.domain + upload.key).appendTo($div1);
        // Div 2
        var $div2 = $("<div></div>").addClass("form-group form-inline").appendTo($form);
        // Title
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-title").appendTo($div2);
        $("<input/>").attr("type", "text").addClass("form-control")
            .attr("id", "input-book-title").attr("placeholder", "书名").appendTo($div2);
        // Author
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-author").appendTo($div2);
        $("<input/>").attr("type", "text").addClass("form-control")
            .attr("id", "input-book-author").attr("placeholder", "作者").appendTo($div2);
        // Translator
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-translator").appendTo($div2);
        $("<input/>").attr("type", "text").addClass("form-control")
            .attr("id", "input-book-translator").attr("placeholder", "译者").appendTo($div2);
        // Book cover
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-cover").appendTo($div2);
        $("<input/>").attr("type", "text").addClass("form-control")
            .attr("id", "input-book-cover").attr("placeholder", "封面路径").appendTo($div2);
        // Div 3
        var $div3 = $("<div></div>").addClass("form-group").appendTo($form);
        $("<label></label>").addClass("control-label sr-only").attr("for", "input-book-comments").appendTo($div2);
        $("<textarea></textarea>").addClass("form-control").attr("rows", "5").attr("id", "input-book-comments")
            .attr("placeholder", "图书评价").appendTo($div3);
        // Div 4
        var $div4 = $("<div></div>").addClass("form-group text-center").appendTo($form);
        $("<button></button>").attr("type", "button").addClass("btn btn-group-sm btn-primary")
            .append("搜索图书").appendTo($div4);
        $("<button></button>").attr("type", "button").addClass("btn btn-group-sm btn-warning")
            .append("上传封面").appendTo($div4);
        $("<button></button>").attr("type", "button").addClass("btn btn-group-sm btn-success")
            .append("保存图书").appendTo($div4);
    };
});