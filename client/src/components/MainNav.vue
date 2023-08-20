<template>
    <aside :class="`${is_expanded && 'is-expanded'}`">
        <div class="logo">
            <img src="../assets/logo.png" alt="Vue">
        </div>

        <div class="menu-toggle-wrap">
            <button class="menu-toggle" @click="ToggleMenu">
                <span class="material-symbols-outlined">double_arrow
                </span>
            </button>
        </div>

        <h3>Menu</h3>
        <div class="menu">
            <router-link class="button" to="/">
                <span class="material-symbols-outlined">menu_book</span>
                <span class="text">Logbook</span>
            </router-link>
            <router-link class="button" to="/">
                <span class="material-symbols-outlined">logout</span>
                <span class="text">Logout</span>
            </router-link>
            <router-link class="button" to="/">
                <span class="material-symbols-outlined">person</span>
                <span class="text">Account</span>
            </router-link>
            <router-link class="button" to="/">
                <span class="material-symbols-outlined">settings</span>
                <span class="text">Settings</span>
            </router-link>
        </div>
    </aside>
</template>

<script setup>
/* eslint-disable */
import { ref } from 'vue'
import logoURL from '../assets/logo.png'

const is_expanded = ref(localStorage.getItem("is_expanded") === "true")

const ToggleMenu = () => {
	is_expanded.value = !is_expanded.value
	localStorage.setItem("is_expanded", is_expanded.value)
}
</script>

<!-- <box-icon class="menu-icon" name="menu"></box-icon> -->

<style lang="scss" scoped>
aside {
    display: flex;
    flex-direction: column;

    background-color: var(--grey);
    color: var(--light);
    width: calc(2rem + 32px);
    overflow: hidden;
    min-height: 100vh;
    padding: 1rem;

    transition: 0.2s ease-out;

    .menu-toggle-wrap {
        display: flex;
        justify-content: flex-end;
        margin-bottom: 1rem;
        position: relative;
        top: 0;
        transition: 0.2s ease-out;

        .menu-toggle {
            transition: 0.2s ease-out;

            .material-symbols-outlined {
                font-size: 2rem;
                color: var(--light);
                transition: 0.2s ease-out;
            }

            &:hover {
				.material-symbols-outlined {
					color: var(--primary);
					transform: translateX(0.5rem);
				}
			}
        }
    }

    &.is-expanded {
        width: var(--sidebar-width);

        .menu-toggle-wrap {
            top: -3rem;

            .menu-toggle {
                transform: rotate(-180deg);
            }
        }
    }

    .logo {
        margin-bottom: 1rem;

        img {
            width: 2rem;
        }
    }

    @media (max-width: 768px) {
        position: fixed;
        z-index: 99;
    }
}


/* nav {
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
} */

</style>