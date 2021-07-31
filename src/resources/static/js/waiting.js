(function() {
    document.addEventListener('DOMContentLoaded', function() {
        fetchImg()
    });

    function fetchImg() {
        document.getElementById("loading").style.display = "block";
        document.getElementById("download_button").style.display = "none";
        fetch("isFinish", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json; charset=utf-8",
            },
        })
            .then(() => {
                console.log("i m finish");
                document.getElementById("loading").style.display = "none";
                document.getElementById("download_button").style.display = "block";

            })
            .catch(e => {
                document.getElementById("loading").innerHTML = "Some error occured!";
            });
    }


})();