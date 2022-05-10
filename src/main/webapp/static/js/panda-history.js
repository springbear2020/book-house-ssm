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

    var $infoDisplayTable = $("#div-info-display-table");

    /* ========================================= Build the page navigation module =================================== */
    var UPLOAD_RECORD = 0;
    var DOWNLOAD_RECORD = 1;
    var LOGIN_LOG = 2;

    // Build the page navigation module
    var buildPageNavigation = function (pageData, whoClicked) {
        // Build nav than add to the page
        var $nav = $("<nav></nav>").attr("aria-label", "Page navigation").addClass("text-center").appendTo($infoDisplayTable);
        var $ul = $("<ul></ul>").addClass("pagination").appendTo($nav);
        var curPageNum = pageData.pageNum;

        // Not the first page, build the previous page element
        if (!pageData.isFirstPage) {
            var $previousPageLi = $("<li></li>").appendTo($ul);
            var $previousPageLink = $("<a></a>").addClass("previous-page").attr("role", "button").attr("aria-label", "Previous").appendTo($previousPageLi);
            $("<span></span>").attr("aria-hidden", "true").append("&laquo;").appendTo($previousPageLink);
        }

        // Build the nav element
        var pageNumArray = pageData.navigatepageNums;
        $.each(pageNumArray, function (index, val) {
            var $li;
            // Current page num add the active style to prevent click and give it a highlight style
            if (val == curPageNum) {
                $li = $("<li></li>").addClass("active").appendTo($ul);
            } else {
                $li = $("<li></li>").appendTo($ul);
            }
            // Give the page number but not the current number a click event
            var $pageTurnLink = $("<a></a>").attr("role", "button").append(val).appendTo($li);
            if (val != curPageNum) {
                $pageTurnLink.addClass("page-turn");
            }
        });

        // Not the last page, build the next page element
        if (!pageData.isLastPage) {
            var $nextPageLi = $("<li></li>").appendTo($ul);
            var $nextPageLink = $("<a></a>").attr("role", "button").addClass("next-page").attr("aria-label", "Next").appendTo($nextPageLi);
            $("<span></span>").attr("aria-hidden", "true").append("&raquo;").appendTo($nextPageLink);
        }

        // Previous page click event
        $(".previous-page").click(function () {
            var pageNum = curPageNum - 1;
            if (LOGIN_LOG === whoClicked) {
                getUserLoginRecordPageData(pageNum);
            } else if (UPLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, UPLOAD_RECORD);
            } else if (DOWNLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, DOWNLOAD_RECORD);
            }
        });

        // Page navigation link click event
        $(".page-turn").click(function () {
            var pageNum = $(this).text();
            if (LOGIN_LOG === whoClicked) {
                getUserLoginRecordPageData(pageNum);
            } else if (UPLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, UPLOAD_RECORD);
            } else if (DOWNLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, DOWNLOAD_RECORD);
            }
        });

        // Next page click event
        $(".next-page").click(function () {
            var pageNum = curPageNum + 1;
            if (LOGIN_LOG === whoClicked) {
                getUserLoginRecordPageData(pageNum);
            } else if (UPLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, UPLOAD_RECORD);
            } else if (DOWNLOAD_RECORD === whoClicked) {
                getBookRecordPageData(pageNum, DOWNLOAD_RECORD);
            }
        });
    };

    /* ==================================== Display book upload and download record ================================= */
    // Build the record data display table
    var buildRecordInfoTable = function (response) {
        $infoDisplayTable.empty();

        var recordList = response.resultMap.recordPageData.list;

        var headTitle = recordList[0].type === 0 ? "上传记录" : "下载记录";
        // Head title of the page
        $("<h2></h2>").append(headTitle).addClass("sub-header").appendTo($infoDisplayTable);
        // Parent of table
        var $tableParent = $("<div></div>").addClass("table-responsive").appendTo($infoDisplayTable);
        var $table = $("<table></table>").addClass("table table-hover").appendTo($tableParent);
        // Table thead and tbody
        var $thead = $("<thead></thead>").appendTo($table);
        var $tbody = $("<tbody></tbody>").appendTo($table);
        var $trOfThead = $("<tr></tr>").appendTo($thead);
        $("<th></th>").append("序号").appendTo($trOfThead);
        $("<th></th>").append("操作").appendTo($trOfThead);
        $("<th></th>").append("时间").appendTo($trOfThead);
        $("<th></th>").append("书名").appendTo($trOfThead);
        $("<th></th>").append("作者").appendTo($trOfThead);
        // Though the record list in loop
        $.each(recordList, function (index, record) {
            var $trOfBody = $("<tr></tr>").appendTo($tbody);
            var pageSize = response.resultMap.recordPageData.pageSize;
            var pageNum = response.resultMap.recordPageData.pageNum;
            var num = (pageNum - 1) * pageSize + index + 1;
            $("<td></td>").append(num).appendTo($trOfBody);
            var operation = record.type === 0 ? "上传图书" : "下载图书";
            $("<td></td>").append(operation).appendTo($trOfBody);
            $("<td></td>").append(record.time).appendTo($trOfBody);
            $("<td></td>").append(record.title).appendTo($trOfBody);
            $("<td></td>").append(record.author).appendTo($trOfBody);
        });
    };

    // Get the user's book upload record page data by page number
    var getBookRecordPageData = function (pageNum, type) {
        $.ajax({
            url: contextPath + "record/" + pageNum,
            type: "get",
            data: "type=" + type,
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    buildRecordInfoTable(response);
                    buildPageNavigation(response.resultMap.recordPageData, type);
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求个人图书记录失败");
            }
        })
    };

    // Book upload record view click event
    $(".link-upload-record").click(function () {
        getBookRecordPageData(1, UPLOAD_RECORD);
    });

    // Book download record view click event
    $(".link-download-record").click(function () {
        getBookRecordPageData(1, DOWNLOAD_RECORD);
    });

    /* ========================================== Display user login record ========================================= */
    // Build the user's login record page data display table
    var buildLoginLogTable = function (response) {
        $infoDisplayTable.empty();

        var loginList = response.resultMap.loginPageData.list;

        // Head title of the page
        $("<h2></h2>").append("登录记录").addClass("sub-header").appendTo($infoDisplayTable);
        // Parent of table
        var $tableParent = $("<div></div>").addClass("table-responsive").appendTo($infoDisplayTable);
        var $table = $("<table></table>").addClass("table table-hover").appendTo($tableParent);
        // Table thead and tbody
        var $thead = $("<thead></thead>").appendTo($table);
        var $tbody = $("<tbody></tbody>").appendTo($table);
        var $trOfThead = $("<tr></tr>").appendTo($thead);
        $("<th></th>").append("序号").appendTo($trOfThead);
        $("<th></th>").append("时间").appendTo($trOfThead);
        $("<th></th>").append("IP").appendTo($trOfThead);
        $("<th></th>").append("地点").appendTo($trOfThead);
        // Though the record list in loop
        $.each(loginList, function (index, login) {
            var $trOfBody = $("<tr></tr>").appendTo($tbody);
            var pageSize = response.resultMap.loginPageData.pageSize;
            var pageNum = response.resultMap.loginPageData.pageNum;
            var num = (pageNum - 1) * pageSize + index + 1;
            $("<td></td>").append(num).appendTo($trOfBody);
            $("<td></td>").append(login.time).appendTo($trOfBody);
            $("<td></td>").append(login.ip).appendTo($trOfBody);
            $("<td></td>").append(login.location).appendTo($trOfBody);
        });
    };

    // Get the user's login record page data by page number
    var getUserLoginRecordPageData = function (pageNum) {
        $.ajax({
            url: contextPath + "record/login/" + pageNum,
            type: "get",
            dataType: "json",
            success: function (response) {
                if (SUCCESS_CODE === response.code) {
                    buildLoginLogTable(response);
                    buildPageNavigation(response.resultMap.loginPageData, LOGIN_LOG);
                } else {
                    showNoticeModal(response.code, response.msg);
                }
            },
            error: function () {
                showNoticeModal(DANGER_CODE, "请求个人登录记录失败");
            }
        })
    };

    // Login log view click event
    $(".link-login-record").click(function () {
        getUserLoginRecordPageData(1);
    });

    /*
     * After page loaded successfully, get the user login log
     */
    getUserLoginRecordPageData(1);
});