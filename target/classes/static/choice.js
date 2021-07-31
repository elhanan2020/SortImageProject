(function() {
    document.addEventListener('DOMContentLoaded', function() {
        fetchImg()
    });

    function buildGallery(resp) {
        if (resp.length === 0) {
        }
        else {
            let imgRow =  "";
            let count = 1;
            for (let item of resp) {
                /*if(count % 5 === 0 )
                    imgRow += "<div class=\"row\" >";*/

                    imgRow +=   "<div class=\"column\">\n" +
                        "        <label>\n" +
                        "            <input type=checkbox name=\""+item+"\" value=\"\">\n" +
                        "        </label>\n" +
                        "        <img src=\"/getAllImage/"+item +"\" alt=\"Lights\" style=\"width:100%\" data-name=\"\">\n" +
                        "    </div>"
                /*if(count % 5 === 0 )
                    imgRow +="</div>"
                count++;*/
            }
            document.getElementById("enterImg").innerHTML = imgRow;
        }
    }

    function fetchImg() {
        fetch("getAllImg", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
        })
            .then(res => res.json())
            .then(resp => {
                buildGallery(resp);
            })
            .catch(e => {
                document.getElementById("showParticipants").innerHTML = "Some error occured!";
            });
    }
})();