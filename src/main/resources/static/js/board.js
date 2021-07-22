let index ={
    init:function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },

    save:function() {
        // alert('user의 save함수 호출됨');
        let data = {
             title: $("#title").val(),
             content: $("#content").val(),

        };

        $.ajax({
            type:"POST",
            url:"/auth/board",
            data: JSON.stringify(data),
            contentType:"application/json; charset=utf-8", //MIME
            dataType:"json"
        }).done(function(resp){
            alert("글쓰기가 완료되었습니다.");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
}
index.init();
