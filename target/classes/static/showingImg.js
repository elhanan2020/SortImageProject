(function() {
    let listToReturn = [];
    let index = 0;
    let content = {};
    document.addEventListener('DOMContentLoaded', function() {
            fetchImg()
    });

    function fetchImg() {
    fetch("getListImg", {
        method: 'GET',
        headers: {
            "Content-Type": "application/json; charset=utf-8",
        },
    })
        .then(res => res.json())
        .then(resp => {
            if (resp.length === 0) {
            }
            else {
                let imgRow =  "";
                for (let item of resp) {
                    imgRow += "<div class=\"row\" >";
                    for (let item2 of item) {
                        console.log(item2);
                       imgRow += "<input type=\"checkbox\" name=\" "+item2 + "\" value=\"" +item2+ "\">\n" +
                                 "<label for=\"vehicle1\">" +
                                 "<div class=\"column\">\n" +
                                 "   <img src=\"get-image/"+ item2 +" \" alt=\"Lights\" style=\"width:100%\"\n" +
                                 "   data-name = \""+ item2+ "\"  >\n" +
                                 " </div>"+
                                 "</label><br>" ;
                    }
                    imgRow +="</div>"
                }
                document.getElementById("enterImg").innerHTML = imgRow;
            }
        })
        .catch(e => {
            document.getElementById("showParticipants").innerHTML = "Some error occured!";
        });
              /*  setTimeout(fetchImg, 10000);*/
            }
function saveYourChoice() {
        document.querySelectorAll('[type=checkbox]').forEach(item =>{
            if(item.checked) {
                listToReturn[index] = item.name;
                index++
            }
            console.log(listToReturn);
            content = {
                method: "post",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(listToReturn),
            };
            fetch('/sendThePicture', content)
                .then( ()=> {
                    console.log("all is ok");
                }).catch(function (error) {
                console.log(error);
            });
        })
    }

})();