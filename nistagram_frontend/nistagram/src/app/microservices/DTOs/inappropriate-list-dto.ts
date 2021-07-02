export class InappropriateListDTO {
    constructor(
        public id : number,
        public reason : string,
        public usernameOfReporter : string,
        public postId : number,
        public mediaId : number

    ){}
}
