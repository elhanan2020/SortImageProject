(function() {
    document.addEventListener('DOMContentLoaded', function() {
        fetchImg()
    });

    function buildGallery(resp) {
        if (resp.length === 0) {
        }
        else {
            let imgRow =  "";
            for (let item of resp) {
                imgRow +=   "<div>\n" +
                    "        <label>\n" +
                    "        <input type=checkbox name=\""+item+"\" value=\"\">\n" +
                    "        </label>\n" +
                    "        <img src=\"/getAllImage/"+item +"\" alt=\"Img\" style=\"width:100%\" data-name=\"\">\n"
                    + "</div>\n";
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