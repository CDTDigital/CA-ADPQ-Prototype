module.exports = {
    "extends": "google",
    "rules": {
        "arrow-parens": [2, "always"],
        "linebreak-style": 0,
        // Increase the line length warning to match Webstorm default, although shorter lines are better
        "max-len": [1, 120, 4, {
            "ignoreComments": true,
            "ignoreUrls": true
        }],
        "no-negated-condition": 0,
        "no-warning-comments": 0,
        "no-var": 0,
        "object-curly-spacing": 0,
        "one-var": 0,
        "one-var-declaration-per-line": 0,
        "comma-dangle": 0,
        "no-trailing-spaces": "error"
    }
};