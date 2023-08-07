<template>
  <nav>
    <div class="icon">
        <box-icon class="menu-icon" name="menu"></box-icon>
    </div>
    <div class="sidebar">
        <div class="sidebar-content">
            <ul class="lists">
                <li class="list">
                    <a href="#" class="nav-link">
                        <box-icon name='book-open'></box-icon>
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
    z-index: 1;
    top: 80px;
    left: 0;
    height: 100%;
    width: 260px;
    background-color: #fff;
}

</style>