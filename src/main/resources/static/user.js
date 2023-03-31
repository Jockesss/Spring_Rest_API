const url = "api/user";
const userTable = document.getElementById("user-info");
let userShow = " ";
const userInfo = (user) => {

    userShow += `
<tr>
<td class = "id">${user.id}</td>
<td class = "firstname">${user.firstname}</td>
<td class = "lastname">${user.lastname}</td>
<td class = "age">${user.age}</td>
<td class= "email">${user.email}</td>
<td class = "roles">
${user.roles.map(role => role.role.replace('ROLE_', ''))}
<!--<th:block>-->
<!--        <span th:switch="">-->
<!--        <span th:case="'ROLE_ADMIN'">ADMIN</span>-->
<!--        <span th:case="'ROLE_USER'">USER</span>-->
<!--        </span>-->
<!--</th:block>-->
</tr>`

    userTable.innerHTML = userShow;
}

fetch(url)
    .then(res => res.json())
    .then(data => userInfo(data))
    .catch(error => console.log(error))

