export class SettingsPerFollow {
    constructor(
        public id : number,
        public followId : number,
        public profile : string,
        public notifyOnMessage : boolean,
        public notifyOnPost : boolean,
        public notifyOnStory : boolean,
        public notifyOnComment : boolean
    ) {}
}
