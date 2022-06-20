var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

// 댓글 저장
        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });

// 댓글 수정
//해당 선택자'#btn-comment-update'로 선택되는 요소를 모두 선택함.
        document.querySelectorAll('#btn-comment-update').forEach(function (item)
        {
            item.addEventListener('click', function () //클릭 이벤트 발생 시
            {
                const form = this.closest('form'); // closest 함수는 셀렉터(#btn-comment-update)로 잡히는 상위 요소중 가장 근접한 하나를 반환한다. form태그가 잡힐 것이다.
                _this.commentUpdate(form); // 해당 form 으로 업데이트 수행. 함수로 보냄
            });
        });

    },
    save : function () {
        var data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        // 공백 및 빈 문자열 체크
            if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "")
            {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            }
            else
            {
                $.ajax({
                    type: 'POST',
                    url: '/api/posts',
                    dataType: 'json',
                    contentType:'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function() {
                    alert('글이 등록되었습니다.');
                    window.location.href = '/';
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }

    },
    update : function () {
        var data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        const check = confirm("수정하시겠습니까?");
        if (check === true)
        {
            if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "")
            {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            }
            else{
            $.ajax({
                    type: 'PUT',
                    url: '/api/posts/'+data.id,
                    dataType: 'json',
                    contentType:'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function() {
                    alert('글이 수정되었습니다.');
                    window.location.href = '/posts/read/' + data.id;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }


    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
       //댓글 저장
    commentSave : function () {
        const data = {
            postsId: $('#postsId2').val(),
            comment: $('#comment').val()
        }

        // 공백 및 빈 문자열 체크
        if (!data.comment || data.comment.trim() === "") {
            alert("댓글을 입력해주세요");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/posts/' + data.postsId + '/comments',
                dataType: 'TEXT', //주의!
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

//댓글 수정
        commentUpdate : function (form)
        {
            const data = //수정 댓글 객체 생성
            {
                id: form.querySelector('#id').value,
                postsId: form.querySelector('#postsId').value,
                comment: form.querySelector('#comment-content').value
            }

            if (!data.comment || data.comment.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            }
            const con_check = confirm("수정하시겠습니까?");
            if (con_check === true) {
                $.ajax({
                    type: 'PUT',
                    url: '/api/posts/' + data.postsId + '/comments/' + data.id,
                    dataType: 'TEXT',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert('댓글이 수정되었습니다.');
                    window.location.reload();
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        },

        //댓글 삭제
        commentDelete : function (postsId, commentId) {
                const con_check = confirm("삭제하시겠습니까?");
                if (con_check === true) {
                    $.ajax({
                        type: 'DELETE',
                        url: '/api/posts/' + postsId + '/comments/' + commentId,
                        dataType: 'TEXT',
                    }).done(function () {
                        alert('댓글이 삭제되었습니다.');
                        window.location.reload();
                    }).fail(function (error) {
                        alert(JSON.stringify(error));
                    });
            }
        }

};

main.init();
