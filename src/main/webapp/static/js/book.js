$(function () {
    /* ====================================================== Commons =============================================== */
    // Obtain current project context path dynamically
    var contextPath = $("#span-context-path").text();
    // Response code from server
    var RESPONSE_INFO_CODE = 0;
    var RESPONSE_SUCCESS_CODE = 1;
    var RESPONSE_WARNING_CODE = 2;
    var RESPONSE_ERROR_CODE = 3;

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
        if (RESPONSE_INFO_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-info");
        } else if (RESPONSE_SUCCESS_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-success");
        } else if (RESPONSE_WARNING_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-warning");
        } else if (RESPONSE_ERROR_CODE === responseCode) {
            $noticeObj.parent().addClass("alert-danger");
        }
        $noticeObj.text(msg);
        $modalObj.modal('show');
    };

    /* ================================================= Show Book ================================================== */
    // After load page successfully, send an ajax request to server for get book info data in json type
    // get_book_page_data("", 1);

    // Deal with the next and last page click event
    var deal_turn_page_event = function (element, pageNum) {
        element.click(function () {
            var title = $("#input-book-search-title").val();
            get_book_page_data(title, pageNum);
        });
    };

    // Search book by title button click event
    $("#btn-book-search").click(function () {
        var title = $("#input-book-search-title").val();
        get_book_page_data(title, 1);
    });

    // Get book page data though book title and the page number
    var get_book_page_data = function (title, pageNum) {
        // Send an ajax to server for get books by title
        $.ajax({
            url: contextPath + "book/" + pageNum,
            type: "get",
            data: "title=" + title,
            dataType: "json",
            success: function (response) {
                if (RESPONSE_SUCCESS_CODE === response.code) {
                    // Clear the existed book data element
                    $("#div-main-display").empty();
                    build_book_module(response);
                    build_nav_module(response);
                } else {
                    show_notice_modal(response.code, response.msg);
                }
                // To top
                $("body, html").animate({scrollTop: 0}, 1);
            },
            error: function () {
                show_notice_modal(RESPONSE_ERROR_CODE, "请求获取图书数据失败");
            }
        })
    };

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

    // Build the page navigation module
    var build_nav_module = function (response) {
        var $hr = $("<hr/>").addClass("featurette-divider");
        $hr.appendTo("#div-main-display");

        var $ul_page_parent_1 = $("<ul></ul>").addClass("pager");
        // Build the last page
        var $last_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-left").attr("aria-hidden", "true");
        var $last_page_a = $("<a></a>").append($last_page_span).append("上一页");
        var $last_page_li = $("<li></li>").append($last_page_a);
        $ul_page_parent_1.append($last_page_li);
        // Build the current page
        var $cur_page_num_span = $("<span></span>").append(response.resultMap.content.pageNum);
        var $total_pages_span = $("<span></span>").append(response.resultMap.content.pages);
        var $cur_page_a = $("<a></a>").append("第 ").append($cur_page_num_span).append(" / ").append($total_pages_span).append(" 页").attr("style", "background-color: #eee");
        var $cur_page_li = $("<li></li>").append($cur_page_a);
        $ul_page_parent_1.append($cur_page_li);
        // Build the total record counts
        var $total_counts_span = $("<span></span>").append(response.resultMap.content.total);
        var $total_counts_a = $("<a></a>").append("共 ").append($total_counts_span).append(" 条").attr("style", "background-color: #eee");
        var $total_counts_li = $("<li></li>").append($total_counts_a);
        $ul_page_parent_1.append($total_counts_li);
        // Build the next page
        var $next_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-right").attr("aria-hidden", "true");
        var $next_page_a = $("<a></a>").append("下一页").append($next_page_span);
        var $next_page_li = $("<li></li>").append($next_page_a);
        $ul_page_parent_1.append($next_page_li);
        // Add the nav parent
        $("<nav></nav>").append($ul_page_parent_1).appendTo("#div-main-display");

        // TODO Fix the mobile equipments can not display the entire nav in one line，and click blank screen to close the nav bar
        // Deal with the previous page link click event
        if (response.resultMap.content.hasPreviousPage) {
            $last_page_a.attr("role", "button").attr("id", "link-last-page");
            deal_turn_page_event($("#link-last-page"), response.resultMap.content.prePage);
        } else {
            $last_page_a.attr("style", "background-color: #eee");
        }
        // Deal with the nex page link click event
        if (response.resultMap.content.hasNextPage) {
            $next_page_a.attr("role", "button").attr("id", "link-next-page");
            deal_turn_page_event($("#link-next-page"), response.resultMap.content.nextPage);
        } else {
            $next_page_a.attr("style", "background-color: #eee");
        }
    };
});