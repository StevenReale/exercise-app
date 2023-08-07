<template>
  <nav>
    <div class="sidebar">
        <div class="icon">
            <box-icon class="menu-icon" name="menu"></box-icon>
        </div>
        <div class="sidebar-content">
            <ul class="lists">
                <li class="list">
                    <a href="#" class="nav-link">
                        <box-icon name='book-open' class="icons"></box-icon>
                        <span class="link">Logbook</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
  </nav>
</template>

<script>
/* eslint-disable */
export default {

}
</script>

<style scoped>
nav {
    padding: 0px;
    position: fixed;
    top: 50px;
    left: 0px;
    height: 70px;
    width: 100%;
    background: #fff;
    box-shadow: 0 0 1px rgba(0, 0, 0, 0.1);
}

nav .icon {
    display: flex;
    align-items: center;
    margin: 0 24px;
}

.icon .menu-icon {
    color: #333;
    font-size: 24px;
    margin-right: 14px;
    cursor: pointer;
}
nav .sidebar {
    position: fixed;
    top: 80px;
    left: 0;
    height: 100%;
    width: 260px;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
}

.sidebar .sidebar-content {
    padding: 30px 16px;
}

.sidebar-content .list {
    list-style: none;
}

.list .nav-link {
    display: flex;
    align-items: center;
    text-decoration: none;
    background-color: red;
    border-radius: 8px;
    padding: 14px 12px;
}

.nav-link .icons {
    margin-right: 14px;
    font-size: 20px;
    color: #707070;
}

.nav-link .link {
    font-size: 14px;
    color: #707070;
    font-weight: 400;
}

</style>