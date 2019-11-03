import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bo-2-m-owner.reducer';
import { IBo2mOwner } from 'app/shared/model/bo-2-m-owner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBo2mOwnerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Bo2mOwnerDetail extends React.Component<IBo2mOwnerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bo2mOwnerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.bo2mOwner.detail.title">Bo2mOwner</Translate> [<b>{bo2mOwnerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.bo2mOwner.name">Name</Translate>
              </span>
            </dt>
            <dd>{bo2mOwnerEntity.name}</dd>
          </dl>
          <Button tag={Link} to="/entity/bo-2-m-owner" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/bo-2-m-owner/${bo2mOwnerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ bo2mOwner }: IRootState) => ({
  bo2mOwnerEntity: bo2mOwner.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Bo2mOwnerDetail);
