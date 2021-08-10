(function() {
    let listToReturn = [];
    let index = 0;
    let content = {};
    document.addEventListener('DOMContentLoaded', function() {
            fetchImg()
    });

    function buildGallery(resp) {
        if (resp.length === 0) {
            window.location.assign("/download");
        }
        else {
            let imgRow =  "";
            for (let item of resp) {
                imgRow += "<div class=\"grid-rows\">";
                imgRow += "<div class=\"grid-col\">\n" ;
                for (let item2 of item) {
                    console.log(item2);
                    imgRow +=   "<div>\n" +
                        "        <label>\n" +
                        "            <input type=checkbox name=\""+item2+"\" value=\"\">\n" +
                        "        </label>\n" +
                        "        <img src=\"/get-image/"+item2 +"\" alt=\"Lights\" style=\"width:100%\" data-name=\"\">\n" +
                        "    </div>"
                }
                imgRow +="</div>\n " +"</div>";
            }
            document.getElementById("enterImg").innerHTML = imgRow;
        }

    }

    function fetchImg() {
        document.getElementById("animated-header").style.display = "block";
        document.getElementById("choice").style.display = "none";
        fetch("getListImg", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
        })
            .then(res => res.json())
            .then(resp => {
                document.getElementById("animated-header").style.display = "none";
                document.getElementById("choice").style.display = "block";
                buildGallery(resp);


            })
            .catch(e => {
                document.getElementById("showParticipants").innerHTML = "Some error occured!";
            });
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