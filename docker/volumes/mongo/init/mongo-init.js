print('Start executing the init command');
db = db.getSiblingDB("admin");
db.createUser({
    user: "mongodb_exporter",
    pwd: "password",
    roles: [
        {role: "clusterMonitor", db: "admin"},
        {role: "read", db: "local"}
    ]
})
print('End executing the init command');