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

    /* ============================================ Build page content ============================================== */
    var $divBookInfo = $("#div-book-display");

    // Build the book info display module
    var buildBookModule = function (response) {
        var bookList = response.resultMap.bookPageData.list;
        var isCoverLeft = true;
        $.each(bookList, function (index, item) {
            // Build the dividing line
            var $hr = $("<hr/>").addClass("featurette-divider");
            $hr.appendTo($divBookInfo);

            // Set the cover image location in turn
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
            var $download_strong = $("<strong></strong>").append("点&nbsp;&nbsp;击&nbsp;&nbsp;量：");
            var $download_span = $("<span></span>").append(item.downloads);
            var $download_p = $("<p></p>").addClass("lead");
            $download_p.append($download_strong).append($download_span);
            $book_Info_parent.append($download_p);
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
            var $downloadLink = $("<a></a>").attr("role", "button");
            var $cover_img = $("<img/>");
            $cover_img.addClass("featurette-image img-responsive center-block");
            $cover_img.attr("src", contextPath + item.coverPath).attr("width", "333").appendTo($downloadLink);
            $cover_parent.append($downloadLink);

            var $parent = $("<div></div>").addClass("row featurette");
            $parent.append($book_Info_parent).append($cover_parent).appendTo($divBookInfo);
            isCoverLeft = !isCoverLeft;
        });
    };

    // Build the page navigation module
    var buildNavPageModule = function (response) {
        // Build the dividing line
        var $hr = $("<hr/>").addClass("featurette-divider");
        $hr.appendTo($divBookInfo);

        var $ul_page_parent_1 = $("<ul></ul>").addClass("pager");
        // Build the last page
        var $last_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-left").attr("aria-hidden", "true");
        var $last_page_a = $("<a></a>").append($last_page_span).append("上一页");
        var $last_page_li = $("<li></li>").append($last_page_a);
        $ul_page_parent_1.append($last_page_li);
        // Build the current page
        var $cur_page_num_span = $("<span></span>").append(response.resultMap.bookPageData.pageNum);
        var $total_pages_span = $("<span></span>").append(response.resultMap.bookPageData.pages);
        var $cur_page_a = $("<a></a>").append("第 ").append($cur_page_num_span).append(" / ").append($total_pages_span).append(" 页").attr("style", "background-color: #eee");
        var $cur_page_li = $("<li></li>").append($cur_page_a);
        $ul_page_parent_1.append($cur_page_li);
        // Build the total record counts
        // var $total_counts_span = $("<span></span>").append(response.resultMap.bookPageData.total);
        // var $total_counts_a = $("<a></a>").append("共 ").append($total_counts_span).append(" 条").attr("style", "background-color: #eee");
        // var $total_counts_li = $("<li></li>").append($total_counts_a);
        // $ul_page_parent_1.append($total_counts_li);
        // Build the next page
        var $next_page_span = $("<span></span>").addClass("glyphicon glyphicon-chevron-right").attr("aria-hidden", "true");
        var $next_page_a = $("<a></a>").append("下一页").append($next_page_span);
        var $next_page_li = $("<li></li>").append($next_page_a);
        $ul_page_parent_1.append($next_page_li);
        // Add to the nav parent
        $("<nav></nav>").append($ul_page_parent_1).appendTo($divBookInfo);

        // Deal with the previous page link click event
        if (response.resultMap.bookPageData.hasPreviousPage) {
            $last_page_a.attr("role", "button").attr("id", "link-last-page");
            pageTurnEvent($("#link-last-page"), response.resultMap.bookPageData.prePage);
        } else {
            $last_page_a.attr("style", "background-color: #eee");
        }
        // Deal with the nex page link click event
        if (response.resultMap.bookPageData.hasNextPage) {
            $next_page_a.attr("role", "button").attr("id", "link-next-page");
            pageTurnEvent($("#link-next-page"), response.resultMap.bookPageData.nextPage);
        } else {
            $next_page_a.attr("style", "background-color: #eee");
        }
    };

    /* ============================================== Get book data ================================================= */
    // Get book page data though book title and the page number
    var getBookPageData = function (title, pageNum) {
        // Send an ajax to server for get books by title
        $.ajax({
            url: contextPath + "book/" + pageNum,
            type: "get",
            data: "title=" + title,
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    $divBookInfo.empty();
                    buildBookModule(response);
                    buildNavPageModule(response);
                } else {
                    showNoticeModal(response.code, response.msg);
                }
                // To top
                $("body, html").animate({scrollTop: 0}, 1);
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求获取图书数据失败");
            }
        })
    };

    /*
     * After page load successfully, get book page data from server
     */
    getBookPageData("", 1);

    // Deal with the page turn click event
    var pageTurnEvent = function (element, pageNum) {
        element.click(function () {
            var title = $("#input-book-search-title").val();
            getBookPageData(title, pageNum);
        });
    };

    // Search book by title button click event
    $("#btn-book-search").click(function () {
        var title = $("#input-book-search-title").val();
        if (title.length <= 0) {
            showNoticeModal(INFO_CODE, "书名不能为空，请输入书名");
            return false;
        }
        getBookPageData(title, 1);
    });
});