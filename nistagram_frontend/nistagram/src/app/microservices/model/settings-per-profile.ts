export class SettingsPerProfile {
    constructor(
        public id : number,
        public profile : string,
        public notifyOnFollw : boolean,
        public notifyOnAcceptedFollowRequest : boolean
    ) {}
}
