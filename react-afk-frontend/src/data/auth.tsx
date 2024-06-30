
export const getUserAuth = async () => {
    const username = 'admin';
    const password = 'admin';
    const credentials = `${username}:${password}`;
    const encodedCredentials = btoa(credentials);

    return encodedCredentials;
};