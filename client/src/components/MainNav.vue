<template>
    <aside :class="`${is_expanded && 'is-expanded'}`">
        <div class="logo">
            <img src="../assets/logo-2.png" alt="Vue">
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
            <router-link class="button" to="/about">
                <span class="material-symbols-outlined">info</span>
                <span class="text">About</span>
            </router-link>
            <router-link class="button" to="/contact">
                <span class="material-symbols-outlined">mail</span>
                <span class="text">Contact</span>
            </router-link>
        </div>

        <div class="flex"></div>

        <div class="menu">
            <router-link class="button" to="/logout">
                <span class="material-symbols-outlined">logout</span>
                <span class="text">Logout</span>
            </router-link>
            <router-link class="button" to="/settings">
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
    z-index: 98;

    background-color: var(--black);
    color: var(--light);
    width: calc(2rem + 32px);
    overflow: hidden;
    min-height: 100vh;
    padding: 1rem;

    transition: 0.2s ease-out;

    .flex {
        flex: 1 1 0;
    }

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
                color: var(--dark-grey);
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

    h3, .button .text {
        opacity: 0;
        transition: 0.3s ease-out;
    }

    h3 {
        color: var(--dark-grey);
        font-size: 0.75rem;
        margin-bottom: 0.5rem;
        text-transform: uppercase;
    }

    .menu {
        margin: 0 -1rem;

        .button {
            display: flex;
            align-items: center;
            text-decoration: none;

            padding: 0.5rem 1rem;
            transition: 0.2s ease-out;

            .material-symbols-outlined {
                font-size: 2rem;
                color: var(--dark-grey);
                transition: 0.2s ease-out;
            }

            .text {
                color: var(--dark-grey);
                transition: 0.2s ease-out;
            }

            &:hover {
                background-color: var(--light);

                .material-symbols-outlined, .text{
                    color: var(--primary);
                }
            }

            &.router-link-exact-active {
                background-color: var(--light);
                border-right: 5px solid var(--primary);
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

        h3, .button .text {
        opacity: 1;
        }

        .button {
            .material-symbols-outlined {
                margin-right: 0.5rem;
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
        position: absolute;
        z-index: 99;
    }
}

</style>