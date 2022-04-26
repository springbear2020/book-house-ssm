/* ====================================== Show Book Info ====================================== */
$(function () {
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();

    /*
    * After load page successfully,
    * send an ajax request to server for get book info data in json type,
    * parse the response json data then build some element to display data for user
    */
    $.ajax({
        url: contextPath + "book/page/1",
        type: "get",
        data: "title=",
        dataType: "json",
        success: function (response) {
            if (200 === response.code) {
                // Build book module to display book info from server
                build_book_module(response);
                // Build the navigation page
                build_nav_module(response);
                // To top
                $("body, html").animate({scrollTop: 0}, 1);
            } else {
                show_notice_modal(response.msg, "alert-info");
            }
        },
        error: function () {
            show_notice_modal("获取图书数据失败", "alert-danger");
        }
    });

    // Deal with the next and last page click event
    var deal_turn_page_event = function (element, response, pageNum) {
        element.click(function () {
            var title = $("#input-book-search-title").val();
            $.ajax({
                url: contextPath + "book/page/" + pageNum,
                type: "get",
                data: "title=" + title,
                dataType: "json",
                success: function (response) {
                    if (200 === response.code) {
                        $("#div-main-display").empty();
                        build_book_module(response);
                        build_nav_module(response);
                    } else {
                        show_notice_modal(response.msg, "alert-info");
                    }
                    // To top
                    $("body, html").animate({scrollTop: 0}, 1);
                },
                error: function () {
                    show_notice_modal("获取图书数据失败", "alert-danger");
                }
            })
        });
    };

    /*
     * Search book by title
     */
    $("#btn-book-search").click(function () {
        var $input_search = $("#input-book-search-title");
        var title = $input_search.val();

        // Send an ajax to server for get books by title
        $.ajax({
            url: contextPath + "book/page/1",
            type: "get",
            data: "title=" + title,
            dataType: "json",
            success: function (response) {
                if (200 === response.code) {
                    $("#div-main-display").empty();
                    build_book_module(response);
                    build_nav_module(response);
                } else {
                    show_notice_modal(response.msg, "alert-info");
                }
                // To top
                $("body, html").animate({scrollTop: 0}, 1);
            },
            error: function () {
                show_notice_modal("获取图书数据失败", "alert-danger");
            }
        })
    });

    // Build the book info display module
    var build_book_module = function (response) {
        var bookList = response.resultMap.content.list;
        var isCoverLeft = true;
        $.each(bookList, function (index, item) {
            // Build the dividing line
            var $hr = $("<hr/>").addClass("featurette-divider");
            $hr.appendTo("#div-main-display");

            var $book_Info_parent = $("<div></div>");
            if (isCoverLeft) {
                $book_Info_parent.addClass("col-md-7 col-md-push-5");
            } else {
                $book_Info_parent.addClass("col-md-7");
            }
            // Build the title element <p></p>
            var $title_strong = $("<strong></strong>").append("书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：");
            var $title_span = $("<span></span>").append(item.title);
            var $title_p = $("<p></p>").addClass("lead");
            $title_p.append($title_strong).append($title_span);
            $book_Info_parent.append($title_p);
            // Build the author element <p></p>
            var $author_strong = $("<strong></strong>").append("作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：");
            var $author_span = $("<span></span>").append(item.author);
            var $author_p = $("<p></p>").addClass("lead");
            $author_p.append($author_strong).append($author_span);
            $book_Info_parent.append($author_p);
            // Build the translator element <p></p>
            var $translator_strong = $("<strong></strong>").append("译&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：");
            var $translator_span = $("<span></span>").append(item.translator);
            var $translator_p = $("<p></p>").addClass("lead");
            $translator_p.append($translator_strong).append($translator_span);
            $book_Info_parent.append($translator_p);
            // Build the downloads element <p></p>
            var $download_strong = $("<strong></strong>").append("下&nbsp;&nbsp;载&nbsp;&nbsp;量：");
            var $download_span = $("<span></span>").append(item.downloads);
            var $download_a_span = $("<span></span>").addClass("glyphicon glyphicon-save").attr("aria-hidden", "true");
            var $download_a = $("<a></a>").attr("href", "#");
            $download_a.append($download_a_span);
            var $download_p = $("<p></p>").addClass("lead");
            $download_p.append($download_strong).append($download_a).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append($download_span);
            $book_Info_parent.append($download_p);
            // Build the collections element <p></p>
            var $collection_strong = $("<strong></strong>").append("收&nbsp;&nbsp;藏&nbsp;&nbsp;量：");
            var $collection_span = $("<span></span>").append(item.collections);
            var $collection_a_span = $("<span></span>").addClass("glyphicon glyphicon-heart").attr("aria-hidden", "true");
            var $collection_a = $("<a></a>").attr("href", "#");
            $collection_a.append($collection_a_span);
            var $collection_p = $("<p></p>").addClass("lead");
            $collection_p.append($collection_strong).append($collection_a).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append($collection_span);
            $book_Info_parent.append($collection_p);
            // Build the upload username element <p></p>
            var $upload_username_strong = $("<strong></strong>").append("上传用户：");
            var $upload_username_span = $("<span></span>").append(item.uploadUsername);
            var $upload_username_p = $("<p></p>").addClass("lead");
            $upload_username_p.append($upload_username_strong).append($upload_username_span);
            $book_Info_parent.append($upload_username_p);
            // Build the upload time element <p></p>
            var $upload_time_strong = $("<strong></strong>").append("上传时间：");
            var $upload_time_span = $("<span></span>").append(item.uploadTime);
            var $upload_time_p = $("<p></p>").addClass("lead");
            $upload_time_p.append($upload_time_strong).append($upload_time_span);
            $book_Info_parent.append($upload_time_p);
            // Build the comments element <p></p>
            var $comment_strong = $("<strong></strong>").append("图书评价：");
            var $comment_span = $("<span></span>").append(item.comments);
            var $comment_p = $("<p></p>").addClass("lead");
            $comment_p.append($comment_strong).append($comment_span);
            $book_Info_parent.append($comment_p);

            var $cover_parent = $("<div></div>");
            if (isCoverLeft) {
                $cover_parent.addClass("col-md-5 col-md-pull-7");
            } else {
                $cover_parent.addClass("col-md-5");
            }
            // Build the cover image
            var $cover_img = $("<img/>");
            $cover_img.addClass("featurette-image img-responsive center-block");
            $cover_img.attr("data-src", "holder.js/500x500/auto");
            $cover_img.attr("th:src", "@{/" + item.coverPath + "}");
            $cover_img.attr("alt", "Generic placeholder image");
            $cover_img.attr("src", item.coverPath)
            $cover_parent.append($cover_img);

            var $parent = $("<div></div>").addClass("row featurette");
            $parent.append($book_Info_parent).append($cover_parent).appendTo("#div-main-display");
            isCoverLeft = !isCoverLeft;
        });
    };

    // Build the page navigation
    var build_nav_module = function (response) {
        var $hr = $("<hr/>").addClass("featurette-divider");
        $hr.appendTo("#div-main-display");

        var $ul_page_parent_1 = $("<ul></ul>").addClass("pager");
        // Build the current page
        var $cur_page_num_span = $("<span></span>").append(response.resultMap.content.pageNum);
        var $total_pages_span = $("<span></span>").append(response.resultMap.content.pages);
        var $cur_page_a = $("<a></a>").append("第 ").append($cur_page_num_span).append(" / ").append($total_pages_span).append(" 页");
        var $cur_page_li = $("<li></li>").append($cur_page_a);
        $ul_page_parent_1.append($cur_page_li);
        // Build the total record counts
        var $total_counts_span = $("<span></span>").append(response.resultMap.content.total);
        var $total_counts_a = $("<a></a>").append("共 ").append($total_counts_span).append(" 条");
        var $total_counts_li = $("<li></li>").append($total_counts_a);
        $ul_page_parent_1.append($total_counts_li);
        // Next line
        var $ul_page_parent_2 = $("<ul></ul>").addClass("pager");
        // Build the last page
        var $last_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-left").attr("aria-hidden", "true");
        var $last_page_a = $("<a></a>").append($last_page_span).append("上一页");
        var $last_page_li = $("<li></li>").append($last_page_a);
        $ul_page_parent_2.append($last_page_li);
        // Build the next page
        var $next_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-right").attr("aria-hidden", "true");
        var $next_page_a = $("<a></a>").append("下一页").append($next_page_span);
        var $next_page_li = $("<li></li>").append($next_page_a);
        $ul_page_parent_2.append($next_page_li);

        // Add the nav parent
        $("<nav></nav>").append($ul_page_parent_1).append($ul_page_parent_2).appendTo("#div-main-display");

        // Deal with the last and next page click event
        if (response.resultMap.content.hasPreviousPage) {
            $last_page_a.attr("role", "button").attr("id", "link-last-page");
            deal_turn_page_event($("#link-last-page"), response, response.resultMap.content.prePage);
        }
        if (response.resultMap.content.hasNextPage) {
            $next_page_a.attr("role", "button").attr("id", "link-next-page");
            deal_turn_page_event($("#link-next-page"), response, response.resultMap.content.nextPage);
        }
    };

    // Show the notice modal
    var show_notice_modal = function (msg, style) {
        var $modalObj = $("#div-modal-notice");
        var $noticeObj = $("#h-notice-content");
        // Clear the existed style of the notice object
        $noticeObj.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        $noticeObj.parent().addClass(style);
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    // Prevent the default submit action of form
    $("form").on("submit", function (e) {
        return false;
    });
});

/* =========================================== TODO Login =========================================== */

$(function () {
    // Obtain current project context path dynamically
    var contextPath = $("#context-path").text();

    // Open the login modal when click login link
    $("#link-login").click(function () {
        // Can not close it unless click close symbol
        $("#modal-login").modal({
            backdrop: "static"
        })
    });

    // Login button event
    $("#btn-login").click(function () {
        // Fix the problem when user login with do nothing after opening the modal
        if (!verify_username_format()) {
            return false;
        }
        if (!verify_password_format()) {
            return false;
        }

        // Send an ajax request the verify the username and password for user login
        var username = $("#input-login-username").val();
        var password = $("#input-login-password").val();
        $.ajax({
            url: contextPath + "user",
            method: "get",
            data: $("#form-login").serialize(),
            dataType: "json",
            success: function (response) {
                if ("200" == response.status) {
                    location.href = contextPath + "user/main";
                } else {
                    show_notice_modal("100", response.msg);
                }
            },
            error: function () {
                show_notice_modal("100", "登录失败，请稍后重试");
            }
        })
    });

    // Verify the format of the username when content has changed
    $("#input-login-username").change(function () {
        if (!verify_username_format()) {
            return false;
        }
    });

    // Verify the format of the password when content has changed
    $("#input-login-password").change(function () {
        if (!verify_password_format()) {
            return false;
        }
    });

    // Verify username format
    var verify_username_format = function () {
        var $usernameObj = $("#input-login-username");
        var username = $usernameObj.val();
        if (username.length <= 0) {
            show_validate_msg($usernameObj, "error", "用户名不能为空");
            return false;
        } else {
            show_validate_msg($usernameObj, "success", "");
            return true;
        }
    };

    // Verify_password_format
    var verify_password_format = function () {
        var $passwordObj = $("#input-login-password");
        var password = $passwordObj.val();
        if (password.length <= 0) {
            show_validate_msg($passwordObj, "error", "密码不能为空");
            return false;
        } else {
            show_validate_msg($passwordObj, "success", "");
            return true;
        }
    };

    // Show the notice modal
    var show_notice_modal = function (status, msg) {
        var $modalObj = $("#modal-notice");
        var $noticeObj = $("#notice-content");
        // Clear the existed style of the notice object
        $noticeObj.parent().removeClass("alert-info alert-success alert-warning alert-danger");
        // Judge the status in order to add different style of the notice
        if ("200" == status) {
            $noticeObj.parent().addClass("alert-success");
        } else {
            $noticeObj.parent().addClass("alert-danger");
        }
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    // Show validate msg of the form item
    var show_validate_msg = function (element, status, msg) {
        // Clear the existing style and restore the default value of the prompt message
        element.parent().removeClass("has-success has-error");
        element.next("span").text("");
        element.next("span").next("span").removeClass("glyphicon-ok glyphicon-remove");
        if ("success" === status) {
            element.parent().addClass("has-success");
            element.next("span").next("span").addClass("glyphicon-ok");
        } else {
            element.parent().addClass("has-error");
            element.next("span").next("span").addClass("glyphicon-remove");
            element.next("span").text(msg);
        }
    };
});

