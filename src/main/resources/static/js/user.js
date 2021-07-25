let index ={
    init:function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },

    save:function() {
        // alert('user의 save함수 호출됨');
        let data = {
             username: $("#username").val(),
             password: $("#password").val(),
             email: $("#email").val()
        };

        //ajax default가 비동기 호출
        //ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청
        $.ajax({
            type:"PUT",
            url:"/auth/joinProc",
            data: JSON.stringify(data), //http body데이터
            contentType:"application/json; charset=utf-8", //MIME
            dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 문자열으로 온다 (json -> js)
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            // console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
}
index.init();
